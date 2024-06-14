package com.vet.Vet.dto.request.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {
    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Phone is required!")
    private String phone;
    @NotNull(message = "Email is required!")
    @Email(message = "Email is required!")
    private String mail;
    @NotNull(message = "Address is required!")
    private String address;
    @NotNull(message = "City is required!")
    private String city;
}
