package org.example.spring_security_jwt.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
    public class Produit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long id;
        private String nameProduit;
        private Float PrixProduit;
        private String DescriptionProduit;
        private String stocks_medicaments;



        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;



    }




