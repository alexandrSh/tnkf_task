package tnkf.task.service.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnkf.task.model.ws.GetCursOnDateXMLResponse;
import tnkf.task.service.soap.DailyInfoClient;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Slf4j
@Service
public class CbDailyInfoService {

    private DailyInfoClient cbDailyInfoClient;

    @Autowired
    public CbDailyInfoService(DailyInfoClient cbDailyInfoClient) {
        this.cbDailyInfoClient = cbDailyInfoClient;
    }

    public List<Object> getCurrentCursOnDate() {
        List<Object> valuteCursOnDates = new ArrayList<>();
        LocalDate date = LocalDate.now();
        try {
            GetCursOnDateXMLResponse response = cbDailyInfoClient.getCursOnDate(convertDate(date));
            Object valuteData = response.getGetCursOnDateXMLResult();
//            if (valuteData != null) {
//                valuteCursOnDates.addAll(valuteData.getValuteCursOnDates());
//            }
        } catch (DatatypeConfigurationException e) {
            log.error("Date ({}) could not converted. Exception is - {}", date, e);
        }
        return valuteCursOnDates;
    }

    public List<Object> getCursOnDate(XMLGregorianCalendar date) {
        List<Object> valuteCursOnDates = new ArrayList<>();
        GetCursOnDateXMLResponse response = cbDailyInfoClient.getCursOnDate(date);
        Object valuteData = response.getGetCursOnDateXMLResult();
        if (valuteData != null) {
//            valuteCursOnDates.addAll(valuteData.getClass());
        }
        return valuteCursOnDates;
    }

    private XMLGregorianCalendar convertDate(LocalDate date) throws DatatypeConfigurationException {
        log.debug("Get currency rate for date - {}", date);
        GregorianCalendar gcal = GregorianCalendar.from(date.minusDays(12).atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        log.debug("Date has been converted - {}", date.toString());
        return xgcal;
    }
}
