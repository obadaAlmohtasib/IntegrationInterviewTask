package com.digitinary.accounts.dto;

import com.digitinary.accounts.annotation.MultipleOfFive;
import com.digitinary.accounts.annotation.ValidAccountNumber;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferMoneyRequest {

	@NotNull
	@ValidAccountNumber
	String fromAccountNumber;

	@NotNull
	@ValidAccountNumber
	String toAccountNumber;
	
	@Min(value = 5, message = "Transfer Amount must be greater than or equal to 5")
	@MultipleOfFive
	Double transferAmount;
	
}
