package com.digitinary.customers.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.digitinary.customers.config.FeignConfig;
import com.digitinary.customers.dto.AccountRequest;
import com.digitinary.customers.dto.response.AccountResponse;

@FeignClient(name = "accounts-service", url = "http://localhost:9292/digitinary", configuration = FeignConfig.class)
public interface AccountServiceClient {

    @GetMapping("/accounts/{customerId}")
    List<AccountResponse> getCustomerAccounts(@PathVariable("customerId") Long customerId);
    
    @PostMapping("/accounts/{customerId}")
    AccountResponse openAccount(@PathVariable Long customerId, @RequestBody AccountRequest request);
	
}
