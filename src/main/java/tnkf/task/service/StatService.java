package tnkf.task.service;

import tnkf.task.controller.dto.CounterResponse;

/**
 * Statistic service interface.
 *
 * @author Aleksandr_Sharomov
 */
public interface StatService {

    /**
     * Calculate and return ws client statistic.
     *
     * @return CounterResponse
     */
    CounterResponse getStatistic();
}
