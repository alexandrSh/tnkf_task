package tnkf.task.repository;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tnkf.task.model.domen.CounterRecord;

import java.util.List;
import java.util.Map;

/**
 * CounterRepository1.
 *
 * @author Aleksandr_Sharomov
 */
public interface CounterRepository {

    List<CounterRecord> findAll();

    @Transactional(isolation = Isolation.READ_COMMITTED)
    void saveCounters(Map<String, Integer> counters);
}
