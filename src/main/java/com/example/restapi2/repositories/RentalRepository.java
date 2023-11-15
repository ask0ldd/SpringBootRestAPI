package com.example.restapi2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.restapi2.models.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

}
