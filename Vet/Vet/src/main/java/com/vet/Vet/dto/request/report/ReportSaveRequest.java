package com.vet.Vet.dto.request.report;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSaveRequest {
    @NotNull(message = "Description is required!")
    private String description;
    @Positive(message = "Price is required!")
    private double price;
    private int appointment_id;
}
