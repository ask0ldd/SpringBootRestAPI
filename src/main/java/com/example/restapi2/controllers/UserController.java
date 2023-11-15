package com.example.restapi2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi2.models.User;
import com.example.restapi2.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Read - Get all users
     * 
     * @return - An Iterable object of Users
     */
    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

}
