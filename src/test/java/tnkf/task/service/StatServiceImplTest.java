package tnkf.task.service;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tnkf.task.controller.dto.CounterResponse;
import tnkf.task.model.domen.CounterRecord;
import tnkf.task.repository.CounterRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * StatServiceImplTest.
 *
 * @author Aleksandr_Sharomov
 */
public class StatServiceImplTest {

    private StatServiceImpl statService;
    private CounterRepository counterRepository;

    @Before
    public void setUp() {
        counterRepository = Mockito.mock(CounterRepository.class);
        statService = new StatServiceImpl(counterRepository);
    }

    @Test
    public void getStatistic() {
        List<CounterRecord> counterRecords = new ArrayList<>();
        counterRecords.add(new CounterRecord("all", 10));
        counterRecords.add(new CounterRecord("success", 5));
        counterRecords.add(new CounterRecord("840", 5));
        Mockito.when(counterRepository.findAll()).thenReturn(counterRecords);
        CounterResponse statistic = statService.getStatistic();

        assertThat(statistic, allOf(
                hasProperty("totalSuccess", Matchers.equalTo(5)),
                hasProperty("total", Matchers.equalTo(10)),
                hasProperty("byCurrency", Matchers.hasEntry("840", 5))
        ));
    }

    @Test
    public void getStatistic_empty() {
        Mockito.when(counterRepository.findAll()).thenReturn(Collections.emptyList());
        CounterResponse statistic = statService.getStatistic();

        assertThat(statistic, allOf(
                hasProperty("totalSuccess", Matchers.equalTo(0)),
                hasProperty("total", Matchers.equalTo(0))
        ));

        assertTrue(statistic.getByCurrency().isEmpty());
    }
}