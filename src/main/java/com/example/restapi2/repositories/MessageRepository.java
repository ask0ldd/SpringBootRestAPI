package com.example.restapi2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.restapi2.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
