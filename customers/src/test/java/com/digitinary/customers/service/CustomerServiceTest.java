package com.digitinary.customers.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitinary.customers.dto.CustomerRequest;
import com.digitinary.customers.dto.response.CustomerResponse;
import com.digitinary.customers.entity.Customer;
import com.digitinary.customers.entity.CustomerType;
import com.digitinary.customers.repo.CustomerRepository;
import com.digitinary.customers.repo.CustomerTypeRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@Mock
	CustomerTypeRepository customerTypeRepository;
	
	@InjectMocks
	CustomerService customerService;

	@Test
	public void CustomerService_CreateCustomer_Long() {
		CustomerRequest request = new CustomerRequest();
		request.setName("Khalid");
		request.setLegalId("1234567");
		request.setType(0L);
		request.setAddress("Irbid City, Jordan");
		
		Mockito.when(customerRepository.save(Mockito.any(Customer.class)))
			.thenReturn(Customer.builder().id(1L).name("").legalId("1231212").type(null).address("").build());
		
		Long id = customerService.createCustomer(request);
		Assertions.assertThat(id).isEqualTo(1L);
	}
	
	@Test
	public void CustomerService_GetCustomerById_CustomerResponse() {
		Mockito.when(customerRepository.findById(10L))
			.thenReturn(Optional.of(Customer.builder().id(10L).legalId("1234567").type(new CustomerType(0, "investment")).build()));
		
		CustomerResponse customer = customerService.getCustomerById(10L);
		Assertions.assertThat(customer).isNotNull();
		Assertions.assertThat(customer.getLegalId()).hasSize(7);
		
		// Not exist
		customer = customerService.getCustomerById(11L);
		Assertions.assertThat(customer).isNotNull();
		Assertions.assertThat(customer.getLegalId()).isEmpty();
	}
	
}
