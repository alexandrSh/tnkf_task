package tnkf.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionHandler.
 *
 * @author Aleksandr_Sharomov
 */
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.ok().build();
    }
}
