package tnkf.task.service.soap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

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
import tnkf.task.model.ws.GetCursOnDateXMLResponse;
import tnkf.task.model.ws.ValuteCursOnDate;
import tnkf.task.model.ws.ValuteData;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * CbDailyInfoClientTest.
 *
 * @author Aleksandr_Sharomov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CbrServiceConfig.class)
@SpringBootTest
public class CbDailyInfoClientTest {

    @Autowired
    private CbDailyInfoClient cbDailyInfoClient;

    private MockWebServiceServer mockWebServiceServer;

    @Before
    public void setUp() {
        mockWebServiceServer = MockWebServiceServer.createServer(cbDailyInfoClient);
    }

    @Test
    public void testCursOnDate() throws IOException, DatatypeConfigurationException {

        String request = ResoursFile.getResource("/soap/request.xml", Collections.singletonMap("#date#", "2019-05-30"));
        String response = ResoursFile.getResource("/soap/response.xml", Collections.singletonMap("#date#", "20190530"));

        Source requestPayload = new StringSource(request);
        Source responsePayload = new StringSource(response);

        mockWebServiceServer.expect(payload(requestPayload))
                .andRespond(withPayload(responsePayload));


        ZonedDateTime zonedDateTime = LocalDate.of(2019, 5, 30)
                .atStartOfDay(ZoneId.systemDefault());
        GregorianCalendar gcal = GregorianCalendar.from(zonedDateTime);
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);

        GetCursOnDateXMLResponse cursOnDate = cbDailyInfoClient.getCursOnDate(xgcal);

        ValuteData valuteData = cursOnDate.getGetCursOnDateXMLResult().getValuteData();
        String onDate = valuteData.getOnDate();
        assertThat(onDate, equalTo("20190530"));
        List<ValuteCursOnDate> valuteCursOnDate = valuteData.getValuteCursOnDate();
        assertThat(valuteCursOnDate, hasSize(34));

        mockWebServiceServer.verify();
    }


}