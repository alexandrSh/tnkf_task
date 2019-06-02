package tnkf.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnkf.task.controller.dto.CounterResponse;
import tnkf.task.model.domen.CounterRecord;
import tnkf.task.repository.CounterRepository;

import java.util.List;

/**
 * StatServiceImpl.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
@Component
public class StatServiceImpl implements StatService {

    private final CounterRepository counterRepository;

    @Autowired
    public StatServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public CounterResponse getStatistic() {
        List<CounterRecord> counters = counterRepository.findAll();
        log.debug("{} counter records was found");
        CounterResponse.Builder builder = new CounterResponse.Builder();
        for (CounterRecord counterRecord : counters) {
            builder.addCounterValue(counterRecord.getName(), counterRecord.getValue());
        }
        return builder.build();
    }
}
