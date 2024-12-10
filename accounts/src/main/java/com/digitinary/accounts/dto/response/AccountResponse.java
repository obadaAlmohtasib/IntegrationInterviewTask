package com.digitinary.accounts.dto.response;

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//public record AccountResponse(String accountNumber, Double balance, AccountType type, AccountStatus status) {}
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
	String accountNumber; 
	Double balance;
	AccountType type; 
	AccountStatus status;
}
