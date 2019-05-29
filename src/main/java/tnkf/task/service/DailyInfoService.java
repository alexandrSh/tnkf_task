package tnkf.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Slf4j
@Service
public class DailyInfoService {

    private DailyInfoClient cbDailyInfoClient;

    @Autowired
    public DailyInfoService(DailyInfoClient cbDailyInfoClient) {
        this.cbDailyInfoClient = cbDailyInfoClient;
    }

    public List<Object> getCurrentCursOnDate() {

        return null;
    }

    public List<Object> getCursOnDate(XMLGregorianCalendar date) {

        return null;
    }

}
