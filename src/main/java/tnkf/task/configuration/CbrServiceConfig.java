package tnkf.task.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import tnkf.task.service.soap.CbDailyInfoClient;

/**
 * WebServiceConfig.
 *
 * @author Aleksandr_Sharomov
 */
@Configuration
public class CbrServiceConfig {

    private final String defaultRequestUrl;
    private final int readTimeout;
    private final int connectonTimeout;
    private final int maxConections;

    public CbrServiceConfig(@Value("${cb.daily-info.request-url}") String defaultRequestUrl,
                            @Value("${cb.daily-info.timeout.read}") int readTimeout,
                            @Value("${cb.daily-info.timeout.connection}") int connectonTimeout,
                            @Value("${cb.daily-info.connection.max}") int maxConnections) {
        this.defaultRequestUrl = defaultRequestUrl;
        this.readTimeout = readTimeout;
        this.connectonTimeout = connectonTimeout;
        this.maxConections = maxConnections;
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
        client.setDefaultUri(defaultRequestUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.setMessageSender(webServiceMessageSender);
        return client;
    }

    @Bean
    public WebServiceMessageSender webServiceMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setConnectionTimeout(connectonTimeout);
        httpComponentsMessageSender.setReadTimeout(readTimeout);
        httpComponentsMessageSender.setMaxTotalConnections(maxConections);
        return httpComponentsMessageSender;
    }
}
