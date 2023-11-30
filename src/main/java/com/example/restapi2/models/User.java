package com.example.restapi2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/*
 * @Data est une annotation Lombok. Nul besoin d’ajouter les getters et les
 * setters. La librairie Lombok s’en charge pour nous. Très utile pour alléger
 * le code.
 */
@Entity
/*
 * @Entity est une annotation qui indique que la classe correspond à une table
 * de la base de données.
 */
@Table(name = "utilisateurs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
/* spring security needs some UserDetails methods to be implemented */
public class User {

    @Id
    /* GeneratedValue / Identity : autoincrement a number when id is missing */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstname;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastname;

    @Column(name = "email", unique = true) // !!! unique
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    // !!! https://www.baeldung.com/jpa-no-argument-constructor-entity-class
}
