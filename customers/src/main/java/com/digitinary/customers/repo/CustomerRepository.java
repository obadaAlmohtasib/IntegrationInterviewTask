package com.digitinary.customers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitinary.customers.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
