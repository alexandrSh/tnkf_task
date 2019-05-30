package tnkf.task.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tnkf.task.model.domen.CounterRecord;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CounterRepository.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
@Repository
public class CounterRepositoryJdbc implements CounterRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<CounterRecord> findAll() {
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

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", counters.keySet());

        List<String> existCounters = namedParameterJdbcTemplate.query(
                "SELECT id FROM counters WHERE id in (:names)",
                parameters,
                (rs, rowNum) -> rs.getString("id")
        );

        Collections.sort(existCounters);

        for (String name : existCounters) {
            updateCounter(name, counters.get(name));
        }

        Map<String, Integer> newCounters = counters.entrySet().stream()
                .filter(e -> !existCounters.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String, Integer> e : newCounters.entrySet()) {
            try {
                insertCounter(e.getKey(), e.getValue());
            } catch (DataAccessException ex) {
                updateCounter(e.getKey(), e.getValue());
            }
        }

        log.debug("counters {}", existCounters);
    }

    private void insertCounter(String name, Integer value) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", name);
        paramMap.put("value", value);
        String sql = "INSERT INTO counters (id, value) VALUES (:id, :value)";
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    private void updateCounter(String name, Integer value) {
        JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();

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
                // Handle any exception while handling the result set
            }
        };
        jdbcTemplate.setMaxRows(1);
        jdbcTemplate.query(pscf.newPreparedStatementCreator(new Object[]{name}), rch);
    }
}
