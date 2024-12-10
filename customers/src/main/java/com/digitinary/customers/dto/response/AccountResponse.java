package com.digitinary.customers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//public record AccountResponse(String accountNumber, Double balance, String type, String status) {}
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
	String accountNumber; 
	Double balance; 
	String type; 
	String status;
}
