package com.digitinary.customers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitinary.customers.client.AccountServiceClient;
import com.digitinary.customers.dto.AccountRequest;
import com.digitinary.customers.dto.response.AccountResponse;
import com.digitinary.customers.entity.Customer;
import com.digitinary.customers.exception.BusinessException;
import com.digitinary.customers.exception.BusinessException.ErrorSeverity;
import com.digitinary.customers.repo.CustomerRepository;

@Service
public class AccountService {

	private final AccountServiceClient accountServiceClient;
	private final CustomerRepository customerRepository;
	
	public AccountService(AccountServiceClient accountServiceClient, CustomerRepository customerRepository) {
		this.accountServiceClient = accountServiceClient;
		this.customerRepository = customerRepository;
	}

	public AccountResponse openAccount(Long customerId, AccountRequest accountRequest) {
		Customer customer = this.customerRepository.findById(customerId)
				.orElseThrow(() -> new BusinessException(String.format("customer %s not found", customerId), ErrorSeverity.WARN));
		
		return this.accountServiceClient.openAccount(customerId, 
				new AccountRequest(customer.getId(), customer.getLegalId(), accountRequest.getAccountType()));
	}
	
	public List<AccountResponse> getCustomerAccounts(Long customerId) {
		return this.accountServiceClient.getCustomerAccounts(customerId);
	}
	
}
