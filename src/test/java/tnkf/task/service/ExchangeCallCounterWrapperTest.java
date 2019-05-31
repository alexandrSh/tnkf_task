package tnkf.task.service;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tnkf.task.exception.CounterException;
import tnkf.task.exception.DailyInfoException;
import tnkf.task.model.entry.Code;
import tnkf.task.model.entry.ExchangeRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ExchangeCallCounterWrapperTest.
 *
 * @author Aleksandr_Sharomov
 */
public class ExchangeCallCounterWrapperTest {

    private ExchangeCallCounterWrapper exchangeCallCounterWrapper;
    private ExchangeRatesService exchangeRatesService;
    private CounterService counterService;

    @Before
    public void setUp() {
        counterService = new CounterServiceTest();
        exchangeRatesService = mock(ExchangeRatesService.class);
        exchangeCallCounterWrapper = new ExchangeCallCounterWrapper(exchangeRatesService, counterService);
    }

    @Test
    public void getCurrentCursOnDate() {
        ExchangeRate exchangeRate = new ExchangeRate("1",
                BigDecimal.ONE,
                new Code(1, "c"),
                LocalDate.now());

        Optional<ExchangeRate> expectedRate = Optional.of(exchangeRate);

        when(exchangeRatesService.getCurrentCursOnDate(eq(30))).thenReturn(expectedRate);


        Optional<ExchangeRate> resultRate = exchangeCallCounterWrapper.getCurrentCursOnDate(30);

        assertSame(expectedRate.get(), resultRate.get());

        Counter counter = counterService.getCounter();
        verify(counter, times(1)).close();

        assertThat(counter.getCounters(), allOf(
                hasEntry("all", 1),
                hasEntry("success", 1),
                hasEntry("30", 1)
        ));
    }

    @Test
    public void getCurrentCursOnDate_error() {

        when(exchangeRatesService.getCurrentCursOnDate(eq(30))).thenThrow(new RuntimeException("message"));


        Optional<ExchangeRate> resultRate = exchangeCallCounterWrapper.getCurrentCursOnDate(30);

        assertFalse(resultRate.isPresent());

        Counter counter = counterService.getCounter();
        verify(counter, times(1)).close();



        assertThat(counter.getCounters(), hasEntry("all", 1));
        assertThat(counter.getCounters().size(), Matchers.is(1));
    }


    private class CounterServiceTest implements CounterService {

        private Counter counter = spy(
                new Counter() {
                    private Map<String, Integer> map = new HashMap<>();

                    @Override
                    public Map<String, Integer> getCounters() {
                        return map;
                    }

                    @Override
                    public void increment(String... names) {
                        for (String s : names) {
                            map.put(s, 1);
                        }

                    }

                    @Override
                    public void close() throws CounterException {
                        System.out.println("close");
                    }
                });

        @Override
        public Counter getCounter() {
            return counter;
        }

    }
}