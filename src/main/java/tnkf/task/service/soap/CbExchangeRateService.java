package tnkf.task.service.soap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import tnkf.task.exception.DailyInfoException;
import tnkf.task.model.entry.Code;
import tnkf.task.model.entry.ExchangeRate;
import tnkf.task.model.ws.GetCursOnDateXMLResponse;
import tnkf.task.model.ws.ValuteCursOnDate;
import tnkf.task.model.ws.ValuteData;
import tnkf.task.service.ExchangeRatesService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class CbExchangeRateService implements ExchangeRatesService {

    private CbDailyInfoClient cbDailyInfoClient;
    private DateTimeFormatter onDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public CbExchangeRateService(CbDailyInfoClient cbDailyInfoClient) {
        this.cbDailyInfoClient = cbDailyInfoClient;
    }

    public Optional<ExchangeRate> getCurrentCursOnDate(Integer code) {
        return getCurrentCursOnDate(valuteCursOnDate -> code.equals(valuteCursOnDate.getVcode())).stream()
                .filter(er -> code.equals(er.getCode().getOkbCode()))
                .findFirst();
    }


    private List<ExchangeRate> getCurrentCursOnDate(Predicate<? super ValuteCursOnDate> courseFilter) {
        LocalDate date = LocalDate.now();
        try {
            GetCursOnDateXMLResponse response = cbDailyInfoClient.getCursOnDate(convertDate(date));
            GetCursOnDateXMLResponse.GetCursOnDateXMLResult cursOnDate = response.getGetCursOnDateXMLResult();
            ValuteData valuteData = cursOnDate.getValuteData();

            final LocalDate onDate = LocalDate.parse(valuteData.getOnDate(), onDateFormatter);

            if (!date.equals(onDate)) {
                throw new DailyInfoException("CBR has't exchange rate on today");
            }

            return valuteData.getValuteCursOnDate().stream()
                    .filter(courseFilter)
                    .map(valuteCurs -> {
                        Code code = new Code(valuteCurs.getVcode(), valuteCurs.getVchCode());
                        return new ExchangeRate(valuteCurs.getVname(), valuteCurs.getVcurs(), code, onDate);
                    })
                    .collect(Collectors.toList());

        } catch (DatatypeConfigurationException | DateTimeParseException e) {
            log.error("Date ({}) could not converted. Exception is - {}", date, e);
            throw new DailyInfoException("Date format exception", e);
        } catch (Exception e) {
            log.error("Can't get exchange rate", e);
            throw new DailyInfoException("Can't get exchange rate", e);
        }
    }


    private XMLGregorianCalendar convertDate(LocalDate date) throws DatatypeConfigurationException {
        log.debug("Get currency rate for date - {}", date);
        GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        log.debug("Date has been converted - {}", date.toString());
        return xgcal;
    }
}
