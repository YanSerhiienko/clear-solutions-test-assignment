package com.example.clearsolutionstestassignment.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.UnexpectedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private String getDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .date(getDateTime())
                .build();
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectDateRangeException.class)
    public ResponseEntity<ErrorObject> handleIncorrectDateRangeException(IncorrectDateRangeException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .date(getDateTime())
                .build();
        return new ResponseEntity<>(errorObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .date(getDateTime())
                .build();
        return new ResponseEntity<>(errorObject, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<ErrorObject> handleUnexpectedException(UnexpectedException ex) {
        ErrorObject errorObject = ErrorObject.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .date(getDateTime())
                .build();
        return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> exceptionMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(getErrorsMap(exceptionMessages), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
