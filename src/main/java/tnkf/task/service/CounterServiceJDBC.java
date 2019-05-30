package tnkf.task.service;

import tnkf.task.repository.CounterRepository;

import java.util.HashMap;

/**
 * CounterServiceImpl.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterServiceJDBC implements CounterService {

    private final CounterRepository counterRepository;

    public CounterServiceJDBC(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    @Override
    public Counter getCounter() {
        return new JdbcCounter();
    }

    private final class JdbcCounter implements Counter {
        private HashMap<String, Integer> counterMap = new HashMap<>();

        @Override
        public void increment(String... names) {
            for (String name : names) {
                Integer value = counterMap.getOrDefault(name, 0);
                counterMap.put(name, value);
            }
        }

        @Override
        public void close() {
            counterRepository.saveCounters(counterMap);
        }
    }
}
