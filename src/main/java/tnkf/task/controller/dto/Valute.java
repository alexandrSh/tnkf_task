package tnkf.task.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Valute.
 *
 * @author Aleksandr_Sharomov
 */
@Getter
public class Valute {
    private final Integer valuteCode;

    @JsonCreator
    public Valute(@JsonProperty("valuteCode") Integer valuteCode) {
        this.valuteCode = valuteCode;
    }
}
