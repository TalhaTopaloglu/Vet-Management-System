package com.vet.Vet.dto.request.animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
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
    @NotNull(message = "Customer Name is required!")
    private int customerId;
}
