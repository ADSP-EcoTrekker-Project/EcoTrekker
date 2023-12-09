package com.ecotrekker.restapi.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Log4j2
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception);
        final List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", errors.toString()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleJsonMappingException (HttpMessageNotReadableException exception) {
        log.error(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid JSON Format"));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException (ConstraintViolationException exception) {
        log.error(exception);
        return createResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleHttpMediaTypeNotSupportedException (HttpMediaTypeNotSupportedException exception) {
        log.error(exception);
        return createResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> anyException (Exception exception) {
        log.error(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Internal Server Error"));
    }

    private ResponseEntity<Map<String, Object>> createResponse(HttpStatus status, Exception exception) {
        log.error(exception);
        return ResponseEntity.status(status).body(Map.of("message", exception.getMessage()));
    }

}
