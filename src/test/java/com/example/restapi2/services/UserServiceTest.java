package com.example.restapi2.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.restapi2.models.User;
import com.example.restapi2.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User user1 = new User(1L, "Laurent", "GINA", "laurentgina@mail.com", "laurent");
    private final User user2 = new User(2L, "Sophie", "FONCEK", "sophiefoncek@mail.com", "sophie");
    private final User user3 = new User(3L, "Agathe", "FEELING", "agathefeeling@mail.com", "agathe");

    @BeforeEach
    public void init() {
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
    }

    @Test
    @DisplayName("User exists : .getUser(id) should return the expected User")
    public void getUser_ReturnOneUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        Optional<User> collectedUser = userService.getUser(1L);

        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());
    }

    /*
     * @Test
     * 
     * @DisplayName("User exists : .updateUser(id) should return the expected User")
     * public void UpdateUser() {
     * 
     * when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
     * 
     * Optional<User> collectedUser = userService.getUser(1L);
     * 
     * Assertions.assertThat(collectedUser.isPresent()).isTrue();
     * Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
     * Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.
     * getFirstname());
     * Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.
     * getLastname());
     * Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.
     * getPassword());
     * Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.
     * getEmail());
     * 
     * userService.saveUser(new User(1L, "Laurentine", "GINA",
     * "laurentinegina@mail.com", "laurent"));
     * 
     * Optional<User> postUpdateCollectedUser = userService.getUser(1L);
     * 
     * Assertions.assertThat(postUpdateCollectedUser.isPresent()).isTrue();
     * Assertions.assertThat(postUpdateCollectedUser.get().getFirstname()).isEqualTo
     * ("Laurentine");
     * Assertions.assertThat(postUpdateCollectedUser.get().getEmail()).isEqualTo(
     * "laurentinegina@mail.com");
     * }
     */

    @Test
    @DisplayName("User exists : .getUsers(id) should return all the existing User")
    public void getUsers_ReturnAllUsers() {

        ArrayList<User> userCollection = new ArrayList<>();
        userCollection.add(user1);
        userCollection.add(user2);
        userCollection.add(user2);

        when(userRepository.findAll()).thenReturn((Iterable<User>) userCollection);

        Iterable<User> collectedUsers = userService.getUsers();
        Iterator<User> it = collectedUsers.iterator();
        // collectedUsers.forEach(null);

        int i = 0;
        while (it.hasNext()) {
            User itUser = it.next();
            Assertions.assertThat(itUser.getUserId()).isGreaterThan(0);
            Assertions.assertThat(itUser.getFirstname()).isEqualTo(userCollection.get(i).getFirstname());
            Assertions.assertThat(itUser.getLastname()).isEqualTo(userCollection.get(i).getLastname());
            Assertions.assertThat(itUser.getPassword()).isEqualTo(userCollection.get(i).getPassword());
            Assertions.assertThat(itUser.getEmail()).isEqualTo(userCollection.get(i).getEmail());
            i++;
        }
    }

    @Test
    @DisplayName("User exists : .loadUserByUsername(email) should return the expected User")
    public void loadUserByUsername() {

        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.ofNullable(user1));

        Optional<User> collectedUser = userService.getUserByEmail("email@domain.com");

        Assertions.assertThat(collectedUser).isNotNull();
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());
    }

}