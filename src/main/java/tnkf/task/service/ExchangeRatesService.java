package tnkf.task.service;

import tnkf.task.model.entry.ExchangeRate;

import java.util.Optional;

/**
 * ExchageRatesService.
 *
 * @author Aleksandr_Sharomov
 */
public interface ExchangeRatesService {
    Optional<ExchangeRate> getCurrentCursOnDate(Integer code);
}
