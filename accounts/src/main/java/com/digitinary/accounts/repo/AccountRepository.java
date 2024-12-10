package com.digitinary.accounts.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitinary.accounts.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<List<Account>> findAllByCustomerId(Long customerId);
	
	public Optional<Account> findByAccountNumber(String accountNumber);
	
}
