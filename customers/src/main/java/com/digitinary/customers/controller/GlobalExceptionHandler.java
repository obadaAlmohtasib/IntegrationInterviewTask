package com.digitinary.customers.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitinary.customers.exception.BusinessException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception = Exception.class)
	public ResponseEntity<?> handleGenericException(final Exception ex) {
		log.error(ex.getMessage());
		return badRequest(ex.getMessage());
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(final BusinessException ex) {
		log.atLevel(org.slf4j.event.Level.valueOf(ex.getSeverity().toString())).log(ex.getMessage());
		return badRequest(ex.getMessage());
	}
		
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationException(ConstraintViolationException e) {
        return badRequest("Validation failed: " + e.getMessage());
    }
    
    // Handles MethodArgumentNotValidException for @Valid or @Validated annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return badRequest(errorMessages);
    }
    
    private final ResponseEntity<?> badRequest(Object message) {
    	return ResponseEntity.badRequest().body(Map.of("message", message));
    }
	
}
