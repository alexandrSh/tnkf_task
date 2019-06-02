package tnkf.task.service;

import tnkf.task.exception.CounterException;

import java.util.Map;

/**
 * Counter.
 *
 * @author Aleksandr_Sharomov
 */
public interface Counter extends AutoCloseable {

    /**
     * Return current counters.
     *
     * @return map of "name"->"value of counter"
     */
    Map<String, Integer> getCounters();

    /**
     * increment counter wit specific name.
     *
     * @param names names of the counter to increment
     */
    void increment(String... names);

    /**
     * Close counters.
     * @throws CounterException
     */
    @Override
    void close() throws CounterException;
}
