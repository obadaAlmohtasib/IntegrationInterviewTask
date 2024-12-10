package com.digitinary.accounts.controller;

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

import com.digitinary.accounts.constants.AccountStatus;
import com.digitinary.accounts.constants.AccountType;
import com.digitinary.accounts.dto.AccountRequest;
import com.digitinary.accounts.dto.response.AccountResponse;
import com.digitinary.accounts.service.AccountService;

@WebMvcTest(controllers = AccountController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	AccountService accountService;
	
	@Test
	public void AccountController_createAccount_AccountResponse()  throws Exception {
        AccountRequest validRequest = AccountRequest.builder()
        		.customerLegalID("1234567")
        		.customerId(2L)
        		.accountType(AccountType.INVESTMENT)
        		.build();

        // Mock the service behavior
//        Mockito.when(accountService.createAccount(validRequest)).thenReturn(Mockito.any(AccountResponse.class));
        Mockito.when(accountService.createAccount(validRequest)).thenReturn(new AccountResponse("21312", 120.0, AccountType.INVESTMENT, 
        		AccountStatus.ACTIVE));

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/{customerId}", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerLegalID\": \"1234567\", \"customerId\": 2, \"accountType\": 2}"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").exists());
	}
	
	@Test
	public void AccountController_GetCustomerAccounts_List() throws Exception {
        Mockito.when(accountService.getCustomerAccounts(1L)).thenReturn(List.of(
        		new AccountResponse("210045000", 120.0, AccountType.INVESTMENT, AccountStatus.ACTIVE),
        		new AccountResponse("300072001", 1500.0, AccountType.SAVINGS, AccountStatus.INACTIVE)
				));

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{customerId}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}
	
}
