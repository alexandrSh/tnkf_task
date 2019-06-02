package tnkf.task.service;

import tnkf.task.model.entry.ExchangeRate;

import java.util.Optional;

/**
 * Service provide info about exchange rate.
 *
 * @author Aleksandr_Sharomov
 */
public interface ExchangeRatesService {

    /**
     * Provide exchange rate for specific currency.
     * @param code currency
     * @return exchange rate
     */
    Optional<ExchangeRate> getCurrentCursOnDate(Integer code);
}
