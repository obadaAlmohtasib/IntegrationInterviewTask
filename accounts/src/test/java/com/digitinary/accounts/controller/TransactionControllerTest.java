package com.digitinary.accounts.controller;

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

import com.digitinary.accounts.dto.TransferMoneyRequest;
import com.digitinary.accounts.service.TransactionService;

@WebMvcTest(controllers = TransactionController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

	@Autowired
	MockMvc mockMvc;
		
	@MockitoBean
	TransactionService transactionService;
	
	@Test
	public void TransactionController_TransferMoney_ResponseEntity() throws Exception {
        // Prepare a valid TransferMoneyRequest
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountNumber("1301900100");
        request.setToAccountNumber("1308800200");
        request.setTransferAmount(50.0); // Valid transfer amount (multiple of 5)s

        // Mock the service behavior
        Mockito.when(transactionService.transferMoney(request)).thenReturn(true);  // Assume the transfer is successful

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/{customerId}/transfer", "customerId123")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fromAccountNumber\": \"1301900100\", \"toAccountNumber\": \"1308800200\", \"transferAmount\": 50.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expect 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));  // Check the response body to confirm the success flag is true
		
	}
	
}
