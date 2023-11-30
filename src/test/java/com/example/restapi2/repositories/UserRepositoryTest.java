package com.example.restapi2.repositories;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.restapi2.exceptions.UserNotFoundException;
import com.example.restapi2.models.User;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

@SpringBootTest(classes = { com.example.restapi2.Restapi2Application.class })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // reinit context between each tests
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final User user1 = new User(1L, "Laurent", "GINA", "laurentgina@mail.com", "laurent");
    private final User user2 = new User(2L, "Sophie", "FONCEK", "sophiefoncek@mail.com", "sophie");
    private final User user3 = new User(3L, "Agathe", "FEELING", "agathefeeling@mail.com", "agathe");
    private final User user4 = new User(4L, "firstname1", "lastname1", "email1@domain.com", "randomPassword1");
    private final User user4Replacement = new User(1L, "updated firstname3", "updated lastname3",
            "updatedemail3@domain.com",
            "randomPassword1");
    private final User user5 = new User(5L, "firstname2", "lastname2", "email2@domain.com", "randomPassword2");
    private final User user6Invalid = new User(6L, "firstname2", "lastname2", "laurentgina@mail.com",
            "randomPassword2");
    private final User user4Invalid = new User(4L, "firstname2", "lastname2", "laurentgina@mail.com",
            "randomPassword2");

    @BeforeEach
    public void initTest() {
        /*
         * userRepository.deleteById(1L);
         * userRepository.deleteById(2L);
         * userRepository.deleteById(3L);
         */
    }

    @DisplayName("Save() saves one User into DB.")
    @Test
    public void saveUser_ReturnSavedUserFromDB() {

        Assertions.assertThat(userRepository.findById(4L).isPresent()).isFalse();

        userRepository.save(user4);
        // 4L since 3 users are already created when initializing the context
        Optional<User> collectedUser = userRepository.findById(4L);

        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user4.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user4.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user4.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user4.getEmail());
    }

    @DisplayName("Save() with invalid User / redundant email.")
    @Test
    public void saveUser_ThrowsException() {
        Assertions.assertThat(userRepository.findById(4L).isPresent()).isFalse();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user6Invalid);
        });

        Assertions.assertThat(exception.getMessage()).contains("Unique index or primary key violation");
    }

    @DisplayName("FindAll() returns the 5 expected Users")
    @Test
    public void findAll_ReturnFiveSavedUsers() {
        userRepository.save(user4);
        userRepository.save(user5);
        Iterable<User> users = userRepository.findAll();
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(StreamSupport.stream(users.spliterator(), false).count()).isEqualTo(5);
        List<String> emails = new ArrayList<String>();
        emails.add("laurentgina@mail.com");
        emails.add("sophiefoncek@mail.com");
        emails.add("agathefeeling@mail.com");
        emails.add("email1@domain.com");
        emails.add("email2@domain.com");

        int i = 0;
        for (Iterator<User> it = users.iterator(); it.hasNext(); i++) {
            User user = it.next();
            Assertions.assertThat(user.getEmail()).isEqualTo(emails.get(i));
        }
    }

    @DisplayName("FindById() returns the expected user")
    @Test
    public void findById_ReturnOneTargetUser() {
        userRepository.save(user4);
        Optional<User> collectedUser = userRepository.findById(4L);
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user4.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user4.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user4.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user4.getEmail());
    }

    @DisplayName("FindById() missing User")
    @Test
    public void findById_ReturnEmptyOptional() {
        Optional<User> user = userRepository.findById(4L);
        Assertions.assertThat(user.isEmpty()).isTrue();
    }

    @DisplayName("findByEmail() returns the expected user")
    @Test
    public void findByEmail_ReturnOneTargetUser() {
        userRepository.save(user4);
        Optional<User> collectedUser = userRepository.findByEmail("email1@domain.com");
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user4.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user4.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user4.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user4.getEmail());
    }

    @DisplayName("FindByEmail() missing User")
    @Test
    public void findByEmail_ReturnEmptyOptional() {
        Optional<User> user = userRepository.findByEmail("email1@domain.com");
        Assertions.assertThat(user.isEmpty()).isTrue();
    }

    @DisplayName("Delete() returns an empty optional")
    @Test
    public void delete_ReturnAnEmptyOptional() {
        userRepository.save(user4);
        Optional<User> collectedUser = userRepository.findById(4L);
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        userRepository.deleteById(collectedUser.get().getUserId());
        Optional<User> postDeletionCollectedUser = userRepository.findById(4L);
        Assertions.assertThat(postDeletionCollectedUser.isEmpty()).isTrue();
    }

    @DisplayName("Update() replaces the expected user")
    @Test
    public void update_ReplaceTheExpectedUser() {
        userRepository.save(user4);
        Optional<User> collectedUser = userRepository.findById(1L);
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());

        userRepository.save(user4Replacement);
        Optional<User> postUpdateCollectedUser = userRepository.findById(1L);
        Assertions.assertThat(postUpdateCollectedUser.isPresent()).isTrue();
        Assertions.assertThat(postUpdateCollectedUser.get().getFirstname()).isEqualTo("updated firstname3");
        Assertions.assertThat(postUpdateCollectedUser.get().getLastname()).isEqualTo("updated lastname3");
        Assertions.assertThat(postUpdateCollectedUser.get().getPassword()).isEqualTo("randomPassword1");
        Assertions.assertThat(postUpdateCollectedUser.get().getEmail()).isEqualTo("updatedemail3@domain.com");
    }

    @DisplayName("Update() called with an invalid User")
    @Test
    public void update_ThrowsException() {
        userRepository.save(user4);
        Optional<User> collectedUser = userRepository.findById(1L);
        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user1.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user1.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user1.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user1.getEmail());

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user4Invalid);
        });

        Assertions.assertThat(exception.getMessage()).contains("Unique index or primary key violation");
    }
}
