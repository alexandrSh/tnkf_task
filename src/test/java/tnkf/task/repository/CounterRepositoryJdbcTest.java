package tnkf.task.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tnkf.task.model.domen.CounterRecord;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CounterRepositoryJdbcTest.
 *
 * @author Aleksandr_Sharomov
 */
@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CounterRepositoryJdbcTest {

    @Autowired
    private CounterRepositoryJdbc counterRepositoryJdbc;

    @Test
    public void testAddCounters() {

        HashMap<String, Integer> counters = new HashMap<>();

        for (int i = 1; i < 3; i++) {
            counters.put(String.valueOf(i), 1);
        }

        for (int j = 0; j < 10; j++) {
            counterRepositoryJdbc.saveCounters(counters);
        }

        List<CounterRecord> records = counterRepositoryJdbc.findAll();

        Assert.assertThat(records,
                hasItems(
                        hasProperty("name", equalTo("1")),
                        hasProperty("value", equalTo(10))
                ));
        Assert.assertThat(records,
                hasItems(
                        hasProperty("name", equalTo("2")),
                        hasProperty("value", equalTo(10))
                ));
    }

    @Test
    public void testAddCounters_inSeparateThread() throws Exception {

        HashMap<String, Integer> counters = new HashMap<>();

        for (int i = 1; i < 10; i++) {
            counters.put(String.valueOf(i), 1);
        }

        int thread = 2;

        ExecutorService executorService = Executors.newFixedThreadPool(thread);

        Callable<Boolean> task = () -> {
            try {
                for (int j = 0; j < 10; j++) {
                    counterRepositoryJdbc.saveCounters(counters);
                }
                return true;
            } catch (Exception e) {
                Assert.fail(e.getMessage());
            }
            return false;
        };


        Future<Boolean> submit1 = executorService.submit(task);
        Future<Boolean> submit2 = executorService.submit(task);
        executorService.shutdown();


        if (submit1.get() && submit2.get()) {
            List<CounterRecord> records = counterRepositoryJdbc.findAll();
            for (int i = 1; i < 10; i++) {
                Assert.assertThat(records,
                        hasItems(
                                hasProperty("name", equalTo(String.valueOf(i))),
                                hasProperty("value", equalTo(20))
                        ));
            }
        } else {
            Assert.fail("Task didn't complete");
        }


    }

}