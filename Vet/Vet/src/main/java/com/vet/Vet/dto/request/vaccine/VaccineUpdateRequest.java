package com.vet.Vet.dto.request.vaccine;

import com.vet.Vet.entities.Animal;
import com.vet.Vet.entities.Report;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineUpdateRequest {
    @Positive(message = "ID değeri pozitif bir değer olmalı.")
    private int id;
    @NotNull(message = "Name is required.")
    private String name;
    @NotNull(message = "Code is required.")
    private String code;
    private Animal animal;
    private Report report;
}
