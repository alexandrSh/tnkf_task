package tnkf.task.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnkf.task.model.domen.CounterRecord;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JDBC based implementation of {@link CounterRepository}.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
@Repository
public class CounterRepositoryJdbc implements CounterRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CounterRepositoryJdbc(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setMaxRows(1);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CounterRecord> findAll() {
        log.debug("findAll()");
        return namedParameterJdbcTemplate.query(
                "SELECT id, value FROM counters",
                (rs, rowNum) -> {
                    String name = rs.getString("id");
                    int value = rs.getInt("value");
                    return new CounterRecord(name, value);
                }
        );
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void saveCounters(Map<String, Integer> counters) {
        log.debug("saveCounters(): {}", counters);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", counters.keySet());

        List<String> existCounters = namedParameterJdbcTemplate.query(
                "SELECT id FROM counters WHERE id in (:names) order by id",
                parameters,
                (rs, rowNum) -> rs.getString("id")
        );

        for (String name : existCounters) {
            updateCounter(name, counters.get(name));
        }

        List<CounterRecord> newCounters = counters.entrySet().stream()
                .filter(e -> !existCounters.contains(e.getKey()))
                .map(e -> new CounterRecord(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(CounterRecord::getName))
                .collect(Collectors.toList());

        if (!newCounters.isEmpty()) {
            log.debug("found new counters {}", newCounters);
        }

        for (CounterRecord cr : newCounters) {
            if (insertCounter(cr.getName(), cr.getValue()) == 0) {
                log.debug("insert counters {} was failed, try to update", cr.getName());
                updateCounter(cr.getName(), cr.getValue());
            }
        }

        log.debug("saveCounters(): end", existCounters);
    }

    private int insertCounter(String name, Integer value) {
        log.debug("insertCounter(): {}, {}", name, value);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", name);
        paramMap.put("value", value);
        //TODO: Postgres make rollback in case insert fail on duplicate key found
        //String sql = "INSERT INTO counters (id, value) VALUES (:id, :value) ON DUPLICATE KEY UPDATE id = id";
        String sql = "INSERT INTO counters (id, value) VALUES (:id, :value)";
        int insertedRow = 0;
        try {
            insertedRow = namedParameterJdbcTemplate.update(sql, paramMap);
        } catch (DuplicateKeyException e) {
            log.debug("duplicate key exception catch");
        }

        log.debug("insertCounter(): end");
        return insertedRow;
    }

    private void updateCounter(String name, Integer value) {
        log.debug("updateCounter(): {} {}", name, value);

        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "SELECT * FROM counters WHERE id = ? FOR UPDATE",
                Types.VARCHAR);
        pscf.setUpdatableResults(true);
        pscf.setResultSetType(ResultSet.TYPE_FORWARD_ONLY);
        RowCallbackHandler rch = resultSet -> {
            try {
                int currentValue = resultSet.getInt("value");
                resultSet.updateInt("value", currentValue + value);
                resultSet.updateRow();
            } catch (Exception e) {
                log.debug("updateCounter exception", e);
            }
        };
        jdbcTemplate.query(pscf.newPreparedStatementCreator(new Object[]{name}), rch);

        log.debug("updateCounter(): end");
    }
}
