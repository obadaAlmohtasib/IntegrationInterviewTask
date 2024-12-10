package com.digitinary.accounts.repo;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;
import com.digitinary.accounts.entity.Account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AccountRepositoryTest {

	AccountRepository accountRepository;
	
    @PersistenceContext
    private EntityManager entityManager;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    public void setUp() {
        fromAccount = Account.builder()
                .accountNumber("3130016000")
                .customerId(1L)
                .balance(550.0)
                .status(AccountStatus.ACTIVE)
                .type(AccountType.SALARY)
                .build();

        toAccount = Account.builder()
                .accountNumber("3130017000")
                .customerId(2L)
                .balance(1000.0)
                .status(AccountStatus.INACTIVE)
                .type(AccountType.SAVINGS)
                .build();

        accountRepository.saveAndFlush(fromAccount);
        accountRepository.saveAndFlush(toAccount);
    }
    
	@Autowired
	public AccountRepositoryTest(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Test 
	public void AccountRepository_Save_Account() throws InterruptedException {
		Assertions.assertThat(fromAccount).isNotNull();
		Assertions.assertThat(fromAccount.getId()).isGreaterThan(0);
	}
	
	@Test 
	public void AccountRepository_Lock_Column() throws InterruptedException {//		
		System.out.println(fromAccount);
		System.out.println(toAccount);
		entityManager.detach(fromAccount);
		entityManager.detach(toAccount);

        // Simulate threads trying to update the same account
        for (int i=1; i<=10; i++) {
            Thread t = new Thread(() -> {
                try {
                	transferMoney(10.0);
                } catch (OptimisticLockingFailureException e) {
                    System.out.println("Optimistic lock failure in task##");
                }
            });
            t.start();
            t.join();
        }
        
        System.out.println("Final balance: ");
        System.out.println(accountRepository.findById(fromAccount.getId()).orElse(null));
        System.out.println(accountRepository.findById(toAccount.getId()).orElse(null));
	}
	
	// Use @Transactional(propagation = Propagation.REQUIRES_NEW) , because @DataJpaTest inherit from @Transactional
	// which means the entire test (unit/class) is enclosed in one transaction. 
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void transferMoney(Double transferAmount) {
		// Detach entity , to be consider as new query and simulate multiple threads/requests
//		entityManager.detach(fromAccount);
//		entityManager.detach(toAccount);
		fromAccount.setBalance(fromAccount.getBalance() - transferAmount);
		toAccount.setBalance(toAccount.getBalance() + transferAmount);
		accountRepository.saveAndFlush(fromAccount);
		accountRepository.saveAndFlush(toAccount);
	}
	
	@Test
	public void AccountRepository_FindByAccountNumber_Account() {
		Account account = accountRepository.findByAccountNumber(fromAccount.getAccountNumber()).orElse(null);
		Assertions.assertThat(account).isNotNull();
	}
	
	@Test
	public void AccountRepository_FindAllByCustomerId_List() {
		List<Account> accounts = accountRepository.findAllByCustomerId(fromAccount.getCustomerId()).orElse(null);
		Assertions.assertThat(accounts).isNotNull();
		Assertions.assertThat(accounts).hasSize(1);
	}
	
}
