package com.example.restapi2.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi2.models.User;
import com.example.restapi2.services.UserService;
import com.example.restapi2.services.ValidationService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private ValidationService validationService;

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

    @PostMapping("/login")
    public ResponseEntity<?> getUserByEmail(@RequestBody String email) {
        Optional<User> userOptional = userService.getUserByEmail(email);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") final Long id, @RequestBody User user) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();

            String firstName = user.getFirstname();
            if (firstName != null && validationService.isName(firstName)) {
                currentUser.setFirstname(firstName);
            }

            String lastName = user.getLastname();
            if (lastName != null && validationService.isName(lastName)) {
                currentUser.setLastname(lastName);
            }

            String mail = user.getEmail();
            if (mail != null && validationService.isEmail(mail)) {
                currentUser.setEmail(mail);
            }

            String password = user.getPassword();
            if (password != null) {
                currentUser.setPassword(password);
            }
            userService.saveUser(currentUser);

            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") final Long id) {
        userService.deleteUser(id);
    }

}
