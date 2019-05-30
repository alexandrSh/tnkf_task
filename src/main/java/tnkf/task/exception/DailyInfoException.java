package tnkf.task.exception;

/**
 * DailyInfoException.
 *
 * @author Aleksandr_Sharomov
 */
public class DailyInfoException extends RuntimeException{

    public DailyInfoException(String message) {
        super(message);
    }

    public DailyInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
