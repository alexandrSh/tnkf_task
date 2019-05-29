package tnkf.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tnkf.task.controller.dto.ConterRespose;
import tnkf.task.service.CbDailyInfoService;
import tnkf.task.service.DailyInfoClient;

/**
 * CounterController.
 *
 * @author Aleksandr_Sharomov
 */
@RestController
public class CounterController {

    private final CbDailyInfoService dailyInfoClient;

    @Autowired
    public CounterController(CbDailyInfoService dailyInfoClient) {
        this.dailyInfoClient = dailyInfoClient;
    }

    @GetMapping("/stat")
    public ConterRespose stat() {
        dailyInfoClient.getCurrentCursOnDate();
        return new ConterRespose();
    }
}
