package org.example.spring_security_jwt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private LocalDate dater;
    private LocalDate datep;
    private String statutR;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

}


