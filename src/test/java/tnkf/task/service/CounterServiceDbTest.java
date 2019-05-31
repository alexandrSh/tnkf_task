package tnkf.task.service;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tnkf.task.repository.CounterRepository;

import java.util.Map;

/**
 * CounterServiceDbTest.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterServiceDbTest {
    private CounterServiceDb counterServiceDb;
    private CounterRepository counterRepository;

    @Before
    public void setUp(){
        counterRepository = Mockito.mock(CounterRepository.class);
        counterServiceDb = new CounterServiceDb(counterRepository);
    }

    @Test
    public void testCounter(){
        Counter counter = counterServiceDb.getCounter();
        counter.increment("first", "second", "third");
        counter.increment("second", "third");
        counter.increment( "third");

        Map<String, Integer> counters = counter.getCounters();

        assertThat(counters, allOf(
                hasEntry("first", 1),
                hasEntry("second", 2),
                hasEntry("third", 3)
        ));
    }

    @Test
    public void testRepositoryCall(){
        Counter counter = counterServiceDb.getCounter();
        counter.increment("first", "second", "third");
        counter.close();
        verify(counterRepository, times(1)).saveCounters(any(Map.class));
    }

    @Test
    public void testRepositoryNoCall(){
        Counter counter = counterServiceDb.getCounter();
        counter.close();
        verify(counterRepository, times(0)).saveCounters(any(Map.class));
    }

}