package tnkf.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tnkf.task.controller.dto.CounterResponse;
import tnkf.task.controller.dto.CurrencyCode;
import tnkf.task.model.entry.ExchangeRate;
import tnkf.task.service.ExchangeRatesService;
import tnkf.task.service.StatService;

import java.util.Optional;

/**
 * CounterController.
 *
 * @author Aleksandr_Sharomov
 */
@Slf4j
@RestController
public class CounterController {

    private final ExchangeRatesService exchangeRatesService;
    private final StatService statService;

    @Autowired
    public CounterController(ExchangeRatesService exchangeRatesService, StatService statService) {
        this.exchangeRatesService = exchangeRatesService;
        this.statService = statService;
    }


    @GetMapping(value = "/stat", produces = "application/json")
    public CounterResponse stat() {
        return statService.getStatistic();
    }

    @PostMapping(value = "/action", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<Void> action(CurrencyCode currencyCode) {
        Optional<ExchangeRate> currentCursOnDate = exchangeRatesService.getCurrentCursOnDate(currencyCode.getCurrencyCode());
        log.debug("currentCursOnDate: {}", currentCursOnDate);
        return ResponseEntity.ok().build();
    }


}
