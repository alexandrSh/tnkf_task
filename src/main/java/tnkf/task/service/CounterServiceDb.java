package tnkf.task.service;

import tnkf.task.repository.CounterRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * CounterServiceImpl.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterServiceDb implements CounterService {

    private final CounterRepository counterRepository;

    public CounterServiceDb(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }


    @Override
    public Counter getCounter() {
        return new DbCounter();
    }

    private final class DbCounter implements Counter {
        private HashMap<String, Integer> counterMap = new HashMap<>();

        @Override
        public Map<String, Integer> getCounters() {
            return new HashMap<>(counterMap);
        }

        @Override
        public void increment(String... names) {
            for (String name : names) {
                Integer value = counterMap.getOrDefault(name, 0);
                counterMap.put(name, value + 1);
            }
        }

        @Override
        public void close() {
            if (counterMap.isEmpty()) {
                return;
            }
            counterRepository.saveCounters(counterMap);
        }
    }
}
