package com.example.restapi2.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.restapi2.exceptions.UserNotFoundException;
import com.example.restapi2.models.User;
import com.example.restapi2.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

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

    // .getUser(id)

    @Test
    @DisplayName("User exists : .getUser(id) should return the expected User")
    public void getUser_ReturnOneUser() {

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(user1));

        Optional<User> collectedUser = userService.getUser(1L);

        verify(userRepository, times(1)).findById(Mockito.anyLong());

        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    @DisplayName("User doesn't exist : .getUser(id) should return an empty Optional")
    public void getUser_ReturnEmptyOptional() {

        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Optional<User> collectedUser = userService.getUser(1L);
        
        Assertions.assertThat(collectedUser.isEmpty()).isEqualTo(true);
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

        verify(userRepository, times(1)).findAll();

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

    // .getUserByEmail(email)

    @Test
    @DisplayName("User exists : .getUserByEmail(email) should return the expected User")
    public void getUserByEmail_ReturnOneUser() {

        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.ofNullable(user1));

        Optional<User> collectedUser = userService.getUserByEmail("email@domain.com");

        verify(userRepository, times(1)).findByEmail(Mockito.anyString());

        Assertions.assertThat(collectedUser).isNotNull();
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    @DisplayName("User doesn't exist : .getUserByEmail(email) should return an emptyOptional")
    public void getUserByEmail_ReturnEmptyOptional() {

        when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.ofNullable(null));

        Optional<User> collectedUser = userService.getUserByEmail("email@domain.com");
        
        Assertions.assertThat(collectedUser.isEmpty()).isEqualTo(true);
    }

    // saveUser(user)

    @Test
    @DisplayName("Save User")
    public void saveUser() {

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);

        verify(userRepository, times(3)).save(Mockito.any(User.class));

        User collectedUser = userService.saveUser(user1);

        verify(userRepository, times(4)).save(Mockito.any(User.class));

        Assertions.assertThat(collectedUser).isNotNull();
        Assertions.assertThat(collectedUser.getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.getEmail()).isEqualTo(user1.getEmail());
    }

    // deleteUser(id)

    @Test
    @DisplayName("Delete User")
    public void deleteUser_repoFindByIdAndRepoDeleteCalled() {

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(user1));
        doNothing().when(userRepository).delete(Mockito.any(User.class));

        userService.deleteUser(1L);
        verify(userRepository, times(1)).findById(Mockito.anyLong());
        verify(userRepository, times(1)).delete(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Try Deleting a non-existent User")
    public void deleteNonExistentUser() {

        when(userRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
        
        Assertions.assertThat(exception.getMessage()).isEqualTo("The target user can't be deleted.");
        verify(userRepository, times(1)).findById(Mockito.anyLong());
    }

}
