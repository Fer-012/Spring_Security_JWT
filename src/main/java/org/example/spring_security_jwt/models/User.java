package org.example.spring_security_jwt.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table( name="users")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;

    private int phone;

    @Size(max = 120)
    private String addresse;

    @Size(max = 120)
    private  String password;

    private LocalDate dateNaissance;

    private String specialite;

    private LocalTime horaire_ouverture;

    private LocalTime horaire_fermuture;


    private String jours_ouverture;

    private String description;

    private  Integer age;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> roles.name()); // This allows you to return the role as an authority
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;


    public User(String username, String email,String addresse, String password) {
        this.username = username;
        this.email = email;
        this.addresse = addresse;
        this.password = password;

    }

}
