package tnkf.task.service.soap;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import tnkf.task.model.ws.GetCursOnDateXML;
import tnkf.task.model.ws.GetCursOnDateXMLResponse;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * DailyInfo.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
public class CbDailyInfoClient extends WebServiceGatewaySupport {


    @Value("${cb.daily-info.request-url}")
    private String requestUrl;
    @Value("${cb.daily-info.curs-on-date-action}")
    private String soapAction;

    public GetCursOnDateXMLResponse getCursOnDate(XMLGregorianCalendar date) {
        log.debug("Start getCursOnDate with XMLGregorianCalendar: {}", date);
        GetCursOnDateXMLResponse response = null;
        GetCursOnDateXML request = new GetCursOnDateXML();
        request.setOnDate(date);
        response = (GetCursOnDateXMLResponse) getWebServiceTemplate()
                .marshalSendAndReceive(requestUrl, request, new SoapActionCallback(soapAction));
        log.debug("Finished getCursOnDate");
        return response;
    }

}

