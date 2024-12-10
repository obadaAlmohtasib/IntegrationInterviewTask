package com.digitinary.customers.controller;

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

import com.digitinary.customers.dto.CustomerRequest;
import com.digitinary.customers.dto.response.CustomerResponse;
import com.digitinary.customers.service.CustomerService;

@WebMvcTest(controllers = CustomerController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	CustomerService customerService;
	
	@Test
	public void ControllerTest_CreateCustomer_ResponseDTO() throws Exception {
        CustomerRequest validRequest = new CustomerRequest();
        validRequest.setName("HelloWorld");
        validRequest.setLegalId("6789012");
        validRequest.setType(0L);
        validRequest.setAddress("Amman City, Jordan");
		
        // Mock the service behavior
        Mockito.when(customerService.createCustomer(validRequest)).thenReturn(1L);  

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"HelloWorld\", \"legalId\": \"6789012\", \"type\": 0, \"address\": \"Amman City, Jordan\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expect 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.body.id").value(1));  // Check the id in response body to confirm the customer created
	}
	
	@Test
	public void ControllerTest_GetCustomerById_void() throws Exception {
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(new CustomerResponse("", "", "", ""));  

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Expect 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer").isMap());
	}
	
}
