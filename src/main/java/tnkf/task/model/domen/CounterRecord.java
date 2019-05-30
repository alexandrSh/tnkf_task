package tnkf.task.model.domen;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

/**
 * CounterRecord.
 *
 * @author Aleksandr_Sharomov
 */
@Value
@ToString
@EqualsAndHashCode
public class CounterRecord {
    private String name;
    private Integer value;
}