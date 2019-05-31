package tnkf.task.service;

import tnkf.task.exception.CounterException;

import java.util.Map;

/**
 * Counter.
 *
 * @author Aleksandr_Sharomov
 */
public interface Counter extends AutoCloseable {

    Map<String, Integer> getCounters();

    void increment(String... names);

    @Override
    void close() throws CounterException;
}
