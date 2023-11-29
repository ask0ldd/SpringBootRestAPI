package com.example.restapi2.repositories;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.restapi2.models.User;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
    private final User user5 = new User(5L, "firstname2", "lastname2", "email2@domain.com", "randomPassword2");

    @DisplayName("Save() saves one User into DB.")
    @Test
    public void saveUser_ReturnSavedUserFromDB() {
        Assertions.assertThat(userRepository.findById(4L).isPresent()).isFalse();

        userRepository.save(user4);
        // 4L since 3 users are created when initializing the context
        Optional<User> collectedUser = userRepository.findById(4L);

        Assertions.assertThat(collectedUser.isPresent()).isTrue();
        Assertions.assertThat(collectedUser.get().getUserId()).isGreaterThan(0);
        Assertions.assertThat(collectedUser.get().getFirstname()).isEqualTo(user4.getFirstname());
        Assertions.assertThat(collectedUser.get().getLastname()).isEqualTo(user4.getLastname());
        Assertions.assertThat(collectedUser.get().getPassword()).isEqualTo(user4.getPassword());
        Assertions.assertThat(collectedUser.get().getEmail()).isEqualTo(user4.getEmail());
    }

}
