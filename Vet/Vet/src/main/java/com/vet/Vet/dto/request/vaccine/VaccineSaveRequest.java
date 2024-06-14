package com.vet.Vet.dto.request.vaccine;

import com.vet.Vet.entities.Animal;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {
    @NotNull(message = "Vaccine name is required.")
    private String name;

    @NotNull(message = "Vaccine code is required.")
    private String code;

    @NotNull(message = "Protection start date is required.")
    private LocalDate protectionStartDate;

    @NotNull(message = "Protection finish date is required.")
    private LocalDate protectionFinishDate;

    @Positive(message = "Animal is required.")
    @NotNull(message = "Animal is required.")
    private int animalId;

    private int reportId;
}
