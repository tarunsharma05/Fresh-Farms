package com.cg.freshfarmonlinestore.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler to handle specific exceptions and return custom responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     * 
     * @param resourceNotFoundException The exception thrown when a resource is not found.
     * @param webRequest The current web request.
     * @return A ResponseEntity containing the error details and HTTP status NOT_FOUND.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                  WebRequest webRequest) {
        // Create error details with current timestamp, exception message, and request description
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles APIException.
     * 
     * @param apiException The exception thrown for API-related errors.
     * @param webRequest The current web request.
     * @return A ResponseEntity containing the error details and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorDetails> handleAPIException(APIException apiException, WebRequest webRequest) {
        // Create error details with current timestamp, exception message, and request description
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                apiException.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException.
     * 
     * @param ex The exception thrown when method arguments are not valid.
     * @return A ResponseEntity containing a map of field errors and HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Create a map to store field errors
        Map<String, String> errors = new HashMap<>();
        // Populate the map with field names and corresponding error messages
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles all other exceptions.
     * 
     * @param ex The generic exception thrown.
     * @param webRequest The current web request.
     * @return A ResponseEntity containing the error details and HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest webRequest) {
        // Create error details with current timestamp, exception message, and request description
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
