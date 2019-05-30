package tnkf.task.service;

import tnkf.task.exception.CounterException;

/**
 * Counter.
 *
 * @author Aleksandr_Sharomov
 */
public interface Counter extends AutoCloseable {
    void increment(String... names);

    @Override
    void close() throws CounterException;
}
