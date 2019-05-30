package tnkf.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tnkf.task.controller.dto.CenterStatistic;
import tnkf.task.controller.dto.Valute;
import tnkf.task.repository.CounterRepository;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.StatService;

import java.util.HashMap;

/**
 * CounterController.
 *
 * @author Aleksandr_Sharomov
 */
@RestController
public class CounterController {

    private final ExchangeRatesService exchangeRate;
    private final StatService statService;
    private final CounterRepository counterRepository;

    @Autowired
    public CounterController(ExchangeRatesService exchangeRate, StatService statService, CounterRepository counterRepository) {
        this.exchangeRate = exchangeRate;
        this.statService = statService;
        this.counterRepository = counterRepository;
    }


    @GetMapping("/stat")
    public CenterStatistic stat() {
        return statService.getStatistic();
    }

    @PostMapping("/action")
    public ResponseEntity<Void> action(Valute valute) {
        exchangeRate.getCurrentCursOnDate(valute.getValuteCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/counters")
    public String stats() {
        HashMap<String, Integer> counters = new HashMap<>();
        counters.put("1",1);
        counters.put("3",1);
        counters.put("4",1);
        counterRepository.saveCounters(counters);
        return "s";
    }
}
