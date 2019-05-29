package tnkf.task.controller.dto;

import lombok.Data;

import java.util.Map;

/**
 * ConterRespose.
 *
 * @author Aleksandr_Sharomov
 */
@Data
public class ConterRespose {
    private Integer totalSuccess;
    private Integer total;
    private Map<String, Integer> valute;
}
