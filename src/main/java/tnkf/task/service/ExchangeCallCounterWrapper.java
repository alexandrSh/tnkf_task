package tnkf.task.service;

import lombok.extern.slf4j.Slf4j;
import tnkf.task.model.entry.ExchangeRate;

import java.util.Optional;

/**
 * Wrapper over the service {@link ExchangeRatesService} allows to count the number of successful requests.
 * uses special names in counters:
 * "all" -> all request
 * "success" -> success request
 * and names equal currency code
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

    /**
     * Count the number of successful requests and call the real {@link ExchangeRatesService} method.
     *
     * @param code currency
     * @return ExchangeRate
     */
    @Override
    public Optional<ExchangeRate> getCurrentCursOnDate(final Integer code) {
        try (Counter counter = counterService.getCounter()) {
            counter.increment("all");
            Optional<ExchangeRate> currentCursOnDate = delegate.getCurrentCursOnDate(code);
            counter.increment("success", String.valueOf(code));
            return currentCursOnDate;
        } catch (Exception e) {
            log.error("Count operation was failed", e);
            throw e;
        }
    }
}
