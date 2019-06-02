package tnkf.task.service.soap;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;
import tnkf.task.configuration.CbrServiceConfig;
import tnkf.task.exception.DailyInfoException;
import tnkf.task.model.entry.Code;
import tnkf.task.model.entry.ExchangeRate;

import javax.xml.transform.Source;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

/**
 * CbExchangeRateServiceTest.
 *
 * @author Aleksandr_Sharomov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CbrServiceConfig.class)
@SpringBootTest
public class CbExchangeRateServiceTest {
    private DateTimeFormatter requestFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private CbExchangeRateService cbExchangeRateService;
    @Autowired
    private CbDailyInfoClient cbDailyInfoClient;

    private MockWebServiceServer mockWebServiceServer;

    @Before
    public void setUp() {
        mockWebServiceServer = MockWebServiceServer.createServer(cbDailyInfoClient);
        cbExchangeRateService = new CbExchangeRateService(cbDailyInfoClient);
    }

    @Test
    public void getCurrentCursOnDate() throws IOException {
        LocalDate now = LocalDate.now();
        String request = ResoursFile.getResource("/soap/request.xml",
                Collections.singletonMap("#date#", now.format(requestFormatter)));
        String response = ResoursFile.getResource("/soap/response.xml",
                Collections.singletonMap("#date#", now.format(responseFormatter)));

        Source requestPayload = new StringSource(request);
        Source responsePayload = new StringSource(response);

        mockWebServiceServer.expect(payload(requestPayload))
                .andRespond(withPayload(responsePayload));

        Optional<ExchangeRate> exchangeRate = cbExchangeRateService.getCurrentCursOnDate(840);

        if (!exchangeRate.isPresent()) {
            Assert.fail("exchangeRate must be set");
        }

        assertThat(exchangeRate.get(), allOf(
                hasProperty("name", is("Доллар США")),
                hasProperty("value", is(BigDecimal.valueOf(64.9084d))),
                hasProperty("date", is(now)),
                hasProperty("code", is(new Code(840, "USD")))
        ));
    }

    @Test(expected = DailyInfoException.class)
    public void getCurrentCursOnDate_error() throws IOException {
        LocalDate now = LocalDate.now();
        String request = ResoursFile.getResource("/soap/request.xml",
                Collections.singletonMap("#date#", now.format(requestFormatter)));
        String response = ResoursFile.getResource("/soap/response_error.xml",Collections.emptyMap());

        Source requestPayload = new StringSource(request);
        Source responsePayload = new StringSource(response);

        mockWebServiceServer.expect(payload(requestPayload))
                .andRespond(withPayload(responsePayload));

        cbExchangeRateService.getCurrentCursOnDate(840);

    }
}