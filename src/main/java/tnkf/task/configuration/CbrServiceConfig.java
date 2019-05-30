package tnkf.task.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import tnkf.task.repository.CounterRepository;
import tnkf.task.service.CounterService;
import tnkf.task.service.CounterServiceJDBC;
import tnkf.task.service.ExchangeCallCounterWrapper;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.soap.CbDailyInfoClient;
import tnkf.task.service.soap.CbExchangeRateService;

/**
 * WebServiceConfig.
 *
 * @author Aleksandr_Sharomov
 */
@Configuration
public class CbrServiceConfig {

    private final String DEFAULT_REQUEST_URL;

    public CbrServiceConfig(@Value("${cb.daily-info.request-url}") String defaultRequestUrl) {
        this.DEFAULT_REQUEST_URL = defaultRequestUrl;
    }

    @Bean(name = "CbDailyInfoMarshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("tnkf.task.model.ws");
        return marshaller;
    }

    @Bean
    public CbDailyInfoClient dailyInfoClient(@Qualifier("CbDailyInfoMarshaller") Jaxb2Marshaller marshaller) {
        CbDailyInfoClient client = new CbDailyInfoClient();
        client.setDefaultUri(DEFAULT_REQUEST_URL);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public CounterService counterService(CounterRepository counterRepository) {
        return new CounterServiceJDBC(counterRepository);
    }

    @Bean
    public ExchangeRatesService exchangeRatesService(CbDailyInfoClient dailyInfoClient, CounterService counterService) {
        CbExchangeRateService cbExchangeRateService = new CbExchangeRateService(dailyInfoClient);
        return new ExchangeCallCounterWrapper(cbExchangeRateService, counterService);
    }


}
