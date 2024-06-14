package com.vet.Vet.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {
    @Positive(message = "ID değeri pozitif bir değer olmalı.")
    private int id;
    @NotNull(message = "Name is required.")
    private String name;
    @NotNull(message = "Phone is required.")
    private String phone;
    @Email(message = "Mail is required.")
    @NotNull(message = "Mail is required.")
    private String mail;
    @NotNull(message = "Address is required.")
    private String address;
    @NotNull(message = "City is required.")
    private String city;

}
