package tnkf.task.exception;

/**
 * CounterException.
 *
 * @author Aleksandr_Sharomov
 */
public class CounterException extends Exception {

    public CounterException(String message) {
        super(message);
    }

    public CounterException(String message, Throwable cause) {
        super(message, cause);
    }
}
