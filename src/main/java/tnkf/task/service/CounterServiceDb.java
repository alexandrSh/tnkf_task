package tnkf.task.service;

import tnkf.task.exception.CounterException;
import tnkf.task.repository.CounterRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple {@link CounterService} implementation.
 * Use {@link CounterRepository} for store counters.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterServiceDb implements CounterService {

    private final CounterRepository counterRepository;

    public CounterServiceDb(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Counter getCounter() {
        return new DbCounter();
    }

    /**
     * {@link Counter} thread-unsafe implementation.
     */
    private final class DbCounter implements Counter {
        private boolean isClose;
        private HashMap<String, Integer> counterMap = new HashMap<>();

        @Override
        public Map<String, Integer> getCounters() {
            return new HashMap<>(counterMap);
        }

        @Override
        public void increment(String... names) {
            if (isClose) {
                throw new CounterException("Counter closed");
            }

            for (String name : names) {
                Integer value = counterMap.getOrDefault(name, 0);
                counterMap.put(name, value + 1);
            }
        }

        /**
         * Store counters value.
         */
        @Override
        public void close() {
            isClose = true;
            if (counterMap.isEmpty()) {
                return;
            }
            counterRepository.saveCounters(counterMap);
        }
    }
}
