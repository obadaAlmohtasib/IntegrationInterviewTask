package com.digitinary.customers.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.digitinary.customers.dto.CustomerRequest;
import com.digitinary.customers.dto.response.CustomerResponse;
import com.digitinary.customers.entity.Customer;
import com.digitinary.customers.repo.CustomerRepository;
import com.digitinary.customers.repo.CustomerTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerTypeRepository customerTypeRepository;
	
	public CustomerService(CustomerRepository customerRepository, CustomerTypeRepository customerTypeRepository) {
		this.customerRepository = customerRepository;
		this.customerTypeRepository = customerTypeRepository;
	}

	public Long createCustomer(CustomerRequest req) {
		Customer customer = this.customerRepository.save(Customer.builder()
				.name(req.getName())
				.legalId(req.getLegalId())
				.type(customerTypeRepository.getReferenceById(req.getType()))
				.address(req.getAddress())
				.build()
				);
		log.info("New customer created: %d", customer.getId());
		return Optional.of(customer).map(Customer::getId).orElse(null);
	}
	
	public CustomerResponse getCustomerById(Long id) {
		return this.customerRepository.findById(id)
				.map(c -> new CustomerResponse(c.getName(), c.getLegalId(), c.getType().getType(), c.getAddress()))
				.orElseGet(() -> new CustomerResponse("", "", "", ""));
	}

	
}
