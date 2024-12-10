package com.digitinary.accounts.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitinary.accounts.dto.TransferMoneyRequest;
import com.digitinary.accounts.entity.Account;
import com.digitinary.accounts.exception.BusinessException;
import com.digitinary.accounts.repo.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@Mock
	MessagePublisherService messagePublisherService;
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	TransactionService transactionService;
	
	@Test
	public void TransactionService_TransferMoney_Boolean() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountNumber("1301900100");
        request.setToAccountNumber("1308800200");
        request.setTransferAmount(10.0);

        Mockito
        	.when(accountRepository.findByAccountNumber("1301900100"))
        	.thenReturn(Optional.of(Account.builder().balance(20.0).build()));
        Mockito
        	.when(accountRepository.findByAccountNumber("1308800200"))
        	.thenReturn(Optional.of(Account.builder().balance(30.0).build()));
        
        Boolean result = this.transactionService.transferMoney(request);
        Assertions.assertThat(result).isTrue();
        
        //////
        
        TransferMoneyRequest request2 = new TransferMoneyRequest();
        request2.setFromAccountNumber("1301900100");
        request2.setToAccountNumber("1308800200");
        request2.setTransferAmount(30.0);

		Assertions.assertThatThrownBy(() -> transactionService.transferMoney(request2))
			.isInstanceOf(BusinessException.class);
	}
	
}
