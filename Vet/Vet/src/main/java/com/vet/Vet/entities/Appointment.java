package com.vet.Vet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_date_id", columnDefinition = "serial")
    private int id;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
    //Değerlendirme Formu 9
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_animal_id", referencedColumnName = "animal_id")
    private Animal animal;
    //Değerlendirme Formu 9
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;

}
