package com.digitinary.customers.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitinary.customers.dto.AccountRequest;
import com.digitinary.customers.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class AccountController {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/{customerId}/accounts")
	public ResponseEntity<?> openAccount(@PathVariable Long customerId, @Valid @RequestBody AccountRequest accountRequest) {
		return ResponseEntity.ok(Map.of("account", this.accountService.openAccount(customerId, accountRequest)));
	}
	
	@GetMapping("/{customerId}/accounts")
	public ResponseEntity<?> getCustomerAccounts(@PathVariable Long customerId) {
		return ResponseEntity.ok(Map.of("accounts", this.accountService.getCustomerAccounts(customerId)));
	}
	
}
