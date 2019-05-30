package tnkf.task.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import tnkf.task.service.soap.DailyInfoClient;

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
    public DailyInfoClient cbDailyInfoClient(@Qualifier("CbDailyInfoMarshaller") Jaxb2Marshaller marshaller) {
        DailyInfoClient client = new DailyInfoClient();
        client.setDefaultUri(DEFAULT_REQUEST_URL);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }


}
