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
public class CurrencyCode {
    private final Integer currencyCode;

    @JsonCreator
    public CurrencyCode(@JsonProperty("currencyCode") Integer currencyCode) {
        this.currencyCode = currencyCode;
    }
}
