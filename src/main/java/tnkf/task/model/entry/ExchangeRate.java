package tnkf.task.model.entry;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ExchangeRate.
 *
 * @author Aleksandr_Sharomov
 */
@Value
@EqualsAndHashCode
public class ExchangeRate {
    private String name;
    private BigDecimal value;
    private Code code;
    private LocalDate date;
}
