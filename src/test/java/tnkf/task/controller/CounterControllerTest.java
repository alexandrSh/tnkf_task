package tnkf.task.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tnkf.task.controller.dto.CounterResponse;
import tnkf.task.exception.DailyInfoException;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.StatService;

/**
 * CounterControllerTest.
 *
 * @author Aleksandr_Sharomov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CounterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRatesService exchangeRatesService;
    @MockBean
    private StatService statService;


    @Test
    public void testAction() throws Exception {
        this.mockMvc.perform(
                post("/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currencyCode\":840}")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testAction_serviceError() throws Exception {
        when(exchangeRatesService.getCurrentCursOnDate(840)).thenThrow(new DailyInfoException("error"));

        this.mockMvc.perform(
                post("/action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currencyCode\":840}")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testStat() throws Exception {
        CounterResponse counterResponse = new CounterResponse.Builder()
                .addCounterValue("all", 10)
                .addCounterValue("840",5)
                .addCounterValue("success", 5)
                .build();

        when(statService.getStatistic()).thenReturn(counterResponse);

        this.mockMvc.perform(
                get("/stat"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(Matchers.is(10)))
                .andExpect(jsonPath("$.totalSuccess").value(Matchers.is(5)))
                .andExpect(jsonPath("$.byCurrency.840").value(Matchers.is(5)));
    }

}