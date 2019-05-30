package tnkf.task.service;

import tnkf.task.model.entry.ExchangeRate;

import java.util.List;

/**
 * ExchageRatesService.
 *
 * @author Aleksandr_Sharomov
 */
public interface ExchangeRatesService {
    List<ExchangeRate> getCurrentCursOnDate(Integer code);
}
