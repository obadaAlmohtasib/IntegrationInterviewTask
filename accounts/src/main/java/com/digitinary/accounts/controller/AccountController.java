package com.digitinary.accounts.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitinary.accounts.dto.AccountRequest;
import com.digitinary.accounts.dto.response.AccountResponse;
import com.digitinary.accounts.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/{customerId}")
	public AccountResponse createAccount(@PathVariable String customerId, @Validated @RequestBody AccountRequest request) {
		return this.accountService.createAccount(request);
	}
	
	@GetMapping("/{customerId}")
	public List<AccountResponse> getCustomerAccounts(@PathVariable Long customerId) {
		return this.accountService.getCustomerAccounts(customerId);
	}
	
}
