package com.digitinary.accounts.dto;

import com.digitinary.accounts.constants.AccountType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {
	
//	@ValidAccountNumber(message = "Account number must start with the 7-digit customer ID and be 10 digits in total.")
//	private String accountNumber;

	@NotNull
	@Pattern(regexp = "^\\d{7}$")
	private String customerLegalID;
	
//	@DecimalMin(value = "0.0", message = "Balance must be greater than or equal to 0")
//	private Double balance;
	
	// Customer ID to link the account to the correct customer
	@NotNull(message = "customerId must not be null")
	private Long customerId;
	
	@NotNull
	private AccountType accountType;

}
