package tnkf.task.exception;

/**
 * CounterException.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterException extends RuntimeException {

    public CounterException(String message) {
        super(message);
    }

    public CounterException(String message, Throwable cause) {
        super(message, cause);
    }
}
