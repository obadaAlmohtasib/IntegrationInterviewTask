package com.digitinary.accounts.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitinary.accounts.dto.response.ResponseDTO;
import com.digitinary.accounts.exception.BusinessException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGeneralException(final Exception ex) {
		log.error(ex.getMessage());
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(final BusinessException ex) {
		log.atLevel(org.slf4j.event.Level.valueOf(ex.getSeverity().toString())).log(ex.getMessage());
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}
		
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseDTO handleValidationException(ConstraintViolationException e) {
        return new ResponseDTO.builder()
        		.addTobody("status", HttpStatus.BAD_REQUEST)
        		.addTobody("message", "Validation failed: " + e.getMessage())
        		.build();
    }
    
    // Handles MethodArgumentNotValidException for @Valid or @Validated annotations
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseDTO.builder()
        		.addTobody("status", HttpStatus.BAD_REQUEST)
        		.addTobody("message", errorMessages)
        		.build();
    }
	
}
