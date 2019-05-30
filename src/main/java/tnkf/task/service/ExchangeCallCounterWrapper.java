package tnkf.task.service;

import lombok.extern.slf4j.Slf4j;
import tnkf.task.exception.CounterException;
import tnkf.task.model.entry.ExchangeRate;

import java.util.Collections;
import java.util.List;

/**
 * CounterService.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
public class ExchangeCallCounterWrapper implements ExchangeRatesService {

    private final ExchangeRatesService delegate;
    private final CounterService counterService;


    public ExchangeCallCounterWrapper(ExchangeRatesService delegate,
                                      CounterService counterService) {
        this.delegate = delegate;
        this.counterService = counterService;
    }

    @Override
    public List<ExchangeRate> getCurrentCursOnDate(final Integer code) {
        try (Counter counter = counterService.getCounter()) {
            List<ExchangeRate> currentCursOnDate = delegate.getCurrentCursOnDate(code);
            counter.increment("success", String.valueOf(code));
            return currentCursOnDate;
        } catch (CounterException e) {
            log.error("Count operation was failed", e);
        }

        return Collections.emptyList();
    }
}