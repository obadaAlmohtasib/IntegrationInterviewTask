package com.digitinary.customers.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @Pattern(regexp = "\\d{7}")
    private String legalId;

    @NotNull
    private Long type;

    @NotNull
    private String address;
	
}
