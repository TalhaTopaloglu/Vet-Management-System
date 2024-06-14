package com.vet.Vet.dto.request.animal;

import com.vet.Vet.entities.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    @Positive(message = "ID değeri pozitif bir değer olmalı.")
    @NotNull
    private int id;
    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Species is required!")
    private String species;
    @NotNull(message = "Breed is required!")
    private String breed;
    @NotNull(message = "Gender is required!")
    private String gender;
    @NotNull(message = "Colour is required!")
    private String colour;
    @NotNull(message = "Date of Birth is required!")
    private LocalDate dateOfBirth;
    private int customerId;

}
