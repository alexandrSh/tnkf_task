package tnkf.task.repository;

import tnkf.task.model.domen.CounterRecord;

import java.util.List;
import java.util.Map;

/**
 * CounterRepository1.
 *
 * @author Aleksandr_Sharomov
 */
public interface CounterRepository {

    /**
     * Find all CounterRecord.
     *
     * @return CounterRecords
     */
    List<CounterRecord> findAll();

    /**
     * Store changes of counters.
     *
     * @param counters map of changes of counters. "name"->"change"
     */
    void saveCounters(Map<String, Integer> counters);
}
