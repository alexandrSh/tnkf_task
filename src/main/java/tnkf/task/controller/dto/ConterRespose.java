package tnkf.task.controller.dto;

import lombok.Value;

import java.util.Map;

/**
 * ConterRespose.
 *
 * @author Aleksandr_Sharomov
 */
@Value
public class ConterRespose {
    private Integer totalSuccess;
    private Integer total;
    private Map<String, Integer> valute;
}
