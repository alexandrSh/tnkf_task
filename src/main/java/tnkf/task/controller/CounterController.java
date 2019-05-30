package tnkf.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tnkf.task.controller.dto.ConterRespose;
import tnkf.task.service.DailyInfoService;

/**
 * CounterController.
 *
 * @author Aleksandr_Sharomov
 */
@RestController
public class CounterController {

    private final DailyInfoService dailyInfoClient;

    @Autowired
    public CounterController(DailyInfoService dailyInfoClient) {
        this.dailyInfoClient = dailyInfoClient;
    }

    @GetMapping("/stat")
    public ConterRespose stat() {
        dailyInfoClient.getCurrentCursOnDate();
        return new ConterRespose();
    }

    @PostMapping("/action")
    public ConterRespose action() {
        dailyInfoClient.getCurrentCursOnDate();
        return new ConterRespose();
    }
}
