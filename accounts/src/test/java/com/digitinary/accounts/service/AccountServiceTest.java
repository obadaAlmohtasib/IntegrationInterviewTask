package com.digitinary.accounts.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;
import com.digitinary.accounts.dto.AccountRequest;
import com.digitinary.accounts.dto.response.AccountResponse;
import com.digitinary.accounts.entity.Account;
import com.digitinary.accounts.exception.BusinessException;
import com.digitinary.accounts.repo.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	private MessagePublisherService messagePublisherService;
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private AccountService accountService;
	
	@Test
	public void AccountService_GetCustomerAccounts_ListOfAccountRequest() {
		Mockito.when(accountRepository.findAllByCustomerId(10L)).thenReturn(Optional.of(List.of(new Account(), new Account())));
		List<AccountResponse> accounts = accountService.getCustomerAccounts(10L);
		Assertions.assertThat(accounts).hasSize(2);
	}
	
	@Test
	public void AccountService_CreateAccount_AccountRequest() {
		Optional<List<Account>> accountsOptional = Optional.of(List.of());
		AccountRequest request = AccountRequest.builder()
				.customerId(0L)
				.customerLegalID("1301900")
				.accountType(AccountType.SALARY)
				.build();
		Account newAccount = Account.builder()
				.accountNumber(request.getCustomerLegalID() + "00" + accountsOptional.get().size())
				.balance(0.0)
				.customerId(request.getCustomerId())
				.type(request.getAccountType())
				.status(AccountStatus.ACTIVE)
				.build();
		
		Mockito.when(accountRepository.findAllByCustomerId(0L)).thenReturn(accountsOptional);
		Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(newAccount);
		
		AccountResponse accountResponse = accountService.createAccount(request);
		Assertions.assertThat(accountResponse).isNotNull();
		Assertions.assertThat(accountResponse.getAccountNumber().substring(0, 7)).isEqualTo(request.getCustomerLegalID());
		Assertions.assertThat(accountResponse.getAccountNumber().length()).isEqualTo(10);
		
		accountsOptional = Optional.of(List.of(
				Account.builder()
				.accountNumber("1202012000")
				.balance(0.0)
				.customerId(0L)
				.type(AccountType.SALARY)
				.status(AccountStatus.ACTIVE)
				.build()
				));
		Mockito.when(accountRepository.findAllByCustomerId(0L)).thenReturn(accountsOptional);
//		cannot use when().then() on the service which is annotated with @InjectMocks ( the system under test).
//		Mockito.doThrow(BusinessException.class).when(accountService).createAccount(request);	
		Assertions.assertThatThrownBy(() -> accountService.createAccount(request)).isInstanceOf(BusinessException.class);
	}
	
}
