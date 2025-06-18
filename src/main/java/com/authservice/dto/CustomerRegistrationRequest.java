package com.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationRequest {
    private String email;
    private String password;
    private String customerId; // Link to customer-service record
}
