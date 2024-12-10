package com.digitinary.customers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//public record CustomerResponse(String name, String legalId, String type, String address) {}
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	String name; 
	String legalId; 
	String type; 
	String address;	
}
