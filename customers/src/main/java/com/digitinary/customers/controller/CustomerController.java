package com.digitinary.customers.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitinary.customers.dto.CustomerRequest;
import com.digitinary.customers.dto.response.ResponseDTO;
import com.digitinary.customers.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping
	public ResponseDTO createCustomer(@Valid @RequestBody CustomerRequest request) {
		return new ResponseDTO.builder()
				.addTobody("id", this.customerService.createCustomer(request))
				.build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
		return ResponseEntity.ok(Map.of("customer", this.customerService.getCustomerById(id)));
	}
	
	
}
