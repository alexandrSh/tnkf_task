package tnkf.task.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import tnkf.task.repository.CounterRepository;
import tnkf.task.service.CounterService;
import tnkf.task.service.CounterServiceDb;
import tnkf.task.service.ExchangeCallCounterWrapper;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.soap.CbDailyInfoClient;
import tnkf.task.service.soap.CbExchangeRateService;

import java.time.Duration;

/**
 * WebServiceConfig.
 *
 * @author Aleksandr_Sharomov
 */
@Configuration
public class CbrServiceConfig {

    private final String defaultRequetUrl;
    private final int timeout;

    public CbrServiceConfig(@Value("${cb.daily-info.request-url}") String defaultRequestUrl,
                            @Value("${cb.daily-info.timeout}") int timeout) {
        this.defaultRequetUrl = defaultRequestUrl;
        this.timeout = timeout;
    }

    @Bean(name = "CbDailyInfoMarshaller")
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("tnkf.task.model.ws");
        return marshaller;
    }

    @Bean
    public CbDailyInfoClient dailyInfoClient(@Qualifier("CbDailyInfoMarshaller") Jaxb2Marshaller marshaller,
                                             WebServiceMessageSender webServiceMessageSender) {
        CbDailyInfoClient client = new CbDailyInfoClient();
        client.setDefaultUri(defaultRequetUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(webServiceMessageSender);
        return client;
    }

    @Bean
    public WebServiceMessageSender webServiceMessageSender() {
        HttpUrlConnectionMessageSender httpComponentsMessageSender = new HttpUrlConnectionMessageSender();
        httpComponentsMessageSender.setConnectionTimeout(Duration.ofMillis(timeout));
        httpComponentsMessageSender.setReadTimeout(Duration.ofMillis(timeout));
        return httpComponentsMessageSender;
    }

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
