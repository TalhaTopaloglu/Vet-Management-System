package com.vet.Vet.dto.request.doctor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    @Positive(message = "ID değeri pozitif bir değer olmalı.")
    @NotNull
    private int id;
    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Phone is required!")
    private String phone;
    @NotNull(message = "Mail is required!")
    private String mail;
    @NotNull(message = "Adress is required!")
    private String address;
    @NotNull(message = "City is required!")
    private String city;
}
