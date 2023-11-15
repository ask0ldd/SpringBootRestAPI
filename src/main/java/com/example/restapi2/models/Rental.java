package com.example.restapi2.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String body;

    @Column(name = "surface")
    private Integer surface;

    @Column(name = "price")
    private Float price;

    @Column(name = "creation")
    private Date creation;

    @Column(name = "update")
    private Date update;
}
