package tnkf.task.model.entry;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Code.
 *
 * @author Aleksandr_Sharomov
 */
@Value
@EqualsAndHashCode
public class Code {
    private Integer okbCode;
    private String characterCode;
}
