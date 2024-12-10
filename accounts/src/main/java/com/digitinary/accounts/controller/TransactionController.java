package com.digitinary.accounts.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitinary.accounts.dto.TransferMoneyRequest;
import com.digitinary.accounts.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class TransactionController {
	
	private TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/{customerId}/transfer")
	public ResponseEntity<?> transferMoney(@PathVariable String customerId, @Valid @RequestBody TransferMoneyRequest request) {
		return ResponseEntity.ok(Map.of("isSuccess", this.transactionService.transferMoney(request)));
	}
	
}
