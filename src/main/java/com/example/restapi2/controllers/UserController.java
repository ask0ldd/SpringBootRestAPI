package com.example.restapi2.controllers;

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
        // throw if iterable empty
        return userService.getUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> getUserByEmail(@RequestBody String email) {
        try {
            User currentUser = userService.getUserByEmail(email);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {
        try {
            User currentUser = userService.getUser(id);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.saveUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>("Can't create the target User.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/{id}")
    // create a dto with only first last and email for requestbody
    public ResponseEntity<?> updateEmployee(@PathVariable("id") final Long id, @RequestBody User user) {

        try {
            User currentUser = userService.getUser(id); // !!! deal with throw

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

            User modifiedUser = userService.saveUser(currentUser);

            return new ResponseEntity<>(/* userService.getUser(id) */ modifiedUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Can't find the requested User.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") final Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User with the following Id deleted : " + id, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("Can't delete the target User.", HttpStatus.BAD_REQUEST);
        }
    }

}
