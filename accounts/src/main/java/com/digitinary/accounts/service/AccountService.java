package com.digitinary.accounts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;
import com.digitinary.accounts.dto.AccountRequest;
import com.digitinary.accounts.dto.response.AccountResponse;
import com.digitinary.accounts.entity.Account;
import com.digitinary.accounts.exception.BusinessException;
import com.digitinary.accounts.exception.BusinessException.ErrorSeverity;
import com.digitinary.accounts.repo.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {

	private MessagePublisherService messagePublisherService;
	private AccountRepository accountRepository;
	
	public AccountService(MessagePublisherService messagePublisherService, AccountRepository accountRepository) {
		this.messagePublisherService = messagePublisherService;
		this.accountRepository = accountRepository;
	}

	public List<AccountResponse> getCustomerAccounts(Long customerId) {
		return this.accountRepository.findAllByCustomerId(customerId)
				.orElseGet(() -> List.of())
				.stream()
				.map(account -> new AccountResponse(account.getAccountNumber(), account.getBalance(), account.getType(), account.getStatus()))
				.collect(Collectors.toList());
	}
	
	public AccountResponse createAccount(AccountRequest request) {
		List<Account> accounts = this.accountRepository.findAllByCustomerId(request.getCustomerId()).orElse(List.of());
		if (accounts.size() >= 10) {
			throw new BusinessException("Customer cannot have more than 10 accounts", ErrorSeverity.WARN);
		}
		if (request.getAccountType() == AccountType.SALARY && accounts.stream().anyMatch(account -> account.getType() == AccountType.SALARY)) {
			throw new BusinessException("Customer can only have one salary account", ErrorSeverity.WARN);
		}
		Account newAccount = this.accountRepository.save(Account.builder()
				.accountNumber(request.getCustomerLegalID() + "00" + accounts.size())
				.balance(0.0)
				.customerId(request.getCustomerId())
				.type(request.getAccountType())
				.status(AccountStatus.ACTIVE)
				.build()
				);
		this.messagePublisherService.sendMessageAccountStatusChanged("New account created: " + newAccount.getAccountNumber());
		return new AccountResponse(newAccount.getAccountNumber(), newAccount.getBalance(), newAccount.getType(), newAccount.getStatus());
	}
	
}
