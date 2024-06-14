package com.vet.Vet.dto.request.report;

import com.vet.Vet.entities.Appointment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateRequest {
    private int id;
    @NotNull(message = "Description is required!")
    private String description;
    @Positive(message = "Price is required!")
    private double price;
    private Appointment appointment;
}
