package com.digitinary.customers.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.digitinary.customers.dto.AccountRequest;
import com.digitinary.customers.dto.response.AccountResponse;
import com.digitinary.customers.service.AccountService;

@WebMvcTest(controllers = AccountController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	AccountService accountService;
	
	@Test
	public void AccountController_OpenAccount_ResponseEntity() throws Exception {
        AccountRequest accountRequest = new AccountRequest(404L, "6789020", "SALARY");

        // Mock the service behavior
        Mockito.when(accountService.openAccount(404L, accountRequest))
        	.thenReturn(new AccountResponse("ssss", 0.0, "", ""));

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/{customerId}/accounts", "404")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": 404, \"customerLegalID\": \"6789020\", \"accountType\": \"SALARY\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.account").isMap());
	}
	
	@Test
	public void AccountController_GetCustomerAccounts_ResponseEntity() throws Exception{
        Mockito.when(accountService.getCustomerAccounts(1L)).thenReturn(List.of(
        		new AccountResponse("210045000", 120.0, "AccountType.INVESTMENT", "AccountStatus.ACTIVE"),
        		new AccountResponse("300072001", 1500.0, "AccountType.SAVINGS", "AccountStatus.INACTIVE")
				));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}/accounts", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accounts").isArray());
	}
	
}
