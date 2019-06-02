package tnkf.task.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tnkf.task.repository.CounterRepository;
import tnkf.task.service.CounterService;
import tnkf.task.service.CounterServiceDb;
import tnkf.task.service.ExchangeCallCounterWrapper;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.soap.CbDailyInfoClient;
import tnkf.task.service.soap.CbExchangeRateService;

/**
 * ApplicationConfig.
 *
 * @author Aleksandr_Sharomov
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterServiceDb(counterRepository);
    }

    @Bean
    public ExchangeRatesService exchangeRatesService(CbDailyInfoClient dailyInfoClient, CounterService counterService) {
        CbExchangeRateService cbExchangeRateService = new CbExchangeRateService(dailyInfoClient);
        return new ExchangeCallCounterWrapper(cbExchangeRateService, counterService);
    }
}
