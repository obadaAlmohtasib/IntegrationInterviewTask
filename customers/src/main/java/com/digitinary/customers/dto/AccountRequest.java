package com.digitinary.customers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRequest {

	// Customer ID to link the account to the correct customer
	@NotNull(message = "customerId must not be null")
	private Long customerId;
	
	@NotNull
	@Pattern(regexp = "^\\d{7}$")
	private String customerLegalID;
	
	@NotNull
	@Pattern(regexp = "SALARY|SAVINGS|INVESTMENT", message = "Invalid type value")
	private String accountType;

}