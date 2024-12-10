package com.digitinary.customers.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.digitinary.customers.entity.Customer;
import com.digitinary.customers.entity.CustomerType;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTest {

	CustomerRepository customerRepository;
	CustomerTypeRepository customerTypeRepository;

	@Autowired
	public CustomerRepositoryTest(CustomerRepository customerRepository, CustomerTypeRepository customerTypeRepository) {
		this.customerRepository = customerRepository;
		this.customerTypeRepository = customerTypeRepository;
	}
	
    private Customer customer;

    @BeforeEach
    public void setUp() {
    	CustomerType customerType = new CustomerType();
    	customerType.setType("investment");
    	
    	customer = Customer.builder()
    			.name("Ali")
    			.legalId("9870050")
    			.type(customerType)
    			.address("Zarqa, Jordan")
                .build();

    	customerTypeRepository.save(customerType);
        customerRepository.save(customer);
    }
	
	@Test
	public void CustomerRepository_Save_CustomerEntity() {
		Assertions.assertThat(customer).isNotNull();
		Assertions.assertThat(customer.getId()).isGreaterThan(0);
	}
	
	@Test
	public void CustomerRepository_FindById_CustomerEntity() {
		Customer entity = customerRepository.findById(customer.getId()).orElse(null);
		Assertions.assertThat(entity).isNotNull();
		Assertions.assertThat(entity.getId()).isGreaterThan(0);
	}
	
}
