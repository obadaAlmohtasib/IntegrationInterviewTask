package com.digitinary.accounts.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitinary.accounts.dto.TransferMoneyRequest;
import com.digitinary.accounts.entity.Account;
import com.digitinary.accounts.exception.BusinessException;
import com.digitinary.accounts.exception.BusinessException.ErrorSeverity;
import com.digitinary.accounts.repo.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

	private final MessagePublisherService messagePublisherService;
	private final AccountRepository accountRepository;
	
	public TransactionService(AccountRepository accountRepository, MessagePublisherService messagePublisherService) {
		this.accountRepository = accountRepository; 
		this.messagePublisherService = messagePublisherService;
	}
	
	@Transactional
	public boolean transferMoney(TransferMoneyRequest req) {
		Account fromAccount = this.accountRepository.findByAccountNumber(req.getFromAccountNumber()).orElseThrow();
		Account toAccount = this.accountRepository.findByAccountNumber(req.getToAccountNumber()).orElseThrow();
		Double transferAmount = req.getTransferAmount();
		if (fromAccount.getBalance() < transferAmount) {
			throw new BusinessException(
					String.format("Account number %s is trying to transfer an amount of %.2f, which exceeds the balance.", 
							fromAccount.getAccountNumber(), fromAccount.getBalance()),
					ErrorSeverity.INFO);
		}
		fromAccount.setBalance(fromAccount.getBalance() - transferAmount);
		toAccount.setBalance(toAccount.getBalance() + transferAmount);
        // Changes will be automatically saved when the transaction commits
        // No explicit save() call needed (it is auto-managed by the transaction)
		log.info("Amount transfered: %.2f, from: %s, to: %s", req.getTransferAmount(), req.getFromAccountNumber(), req.getToAccountNumber());
		return true;
	}
	
	private Supplier<BusinessException> accountNotFound(String accountNumber) {
		return () -> new BusinessException(String.format("The account number '%s' does not exist.", accountNumber), ErrorSeverity.INFO);
	}
	
}
