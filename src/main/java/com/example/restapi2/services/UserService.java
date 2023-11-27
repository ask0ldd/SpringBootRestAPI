package com.example.restapi2.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapi2.exceptions.UserNotFoundException;
import com.example.restapi2.models.User;
import com.example.restapi2.repositories.UserRepository;

import lombok.Data;

@Data
@Service
/*
 * @Service : tout comme l’annotation @Repository, c’est une spécialisation
 * de @Component. Son rôle est donc le même, mais son nom a une valeur
 * sémantique pour ceux qui lisent votre code.
 */
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(final Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Target user can't be found."));
        return user;
    }

    public User getUserByEmail(final String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Target user can't be found."));
        return user;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(final Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Target user can't be deleted."));
        userRepository.delete(user);
    }

    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

}
