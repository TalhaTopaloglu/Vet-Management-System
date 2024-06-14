package com.vet.Vet.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull(message = "Name is required!")
    private String name;
    @NotNull(message = "Phone is required!")
    private String phone;
    @Email(message = "eMail is required!")
    @NotNull(message = "eMail is required!")
    private String mail;
    @NotNull(message = "eMail is required!")
    private String address;
    @NotNull(message = "eMail is required!")
    private String city;
}
