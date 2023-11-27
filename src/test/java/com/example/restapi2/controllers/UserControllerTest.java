package com.example.restapi2.controllers;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;

import com.example.restapi2.models.User;
import com.example.restapi2.services.UserService;
import com.example.restapi2.services.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private ValidationService validationService;

    private final User user1 = new User(1L, "Laurent", "GINA", "laurentgina@mail.com", "laurent");
    private final User user2 = new User(2L, "Sophie", "FONCEK", "sophiefoncek@mail.com", "sophie");
    private final User user3 = new User(3L, "Agathe", "FEELING", "agathefeeling@mail.com", "agathe");

    // When Exception : Failed to load ApplicationContext for
    // [WebMergedContextConfiguration
    // Look at the bottom of the error to check if all required beans have been
    // loaded
    // Here validationService was missing at first
    @Test
    public void GetAllUsers_ReturnUser() throws Exception {
        ArrayList<User> userCollection = new ArrayList<>();
        userCollection.add(user1);
        userCollection.add(user2);
        userCollection.add(user3);
        given(userService.getUsers()).willAnswer((invocation -> (Iterable<User>) userCollection));

        ResultActions response = mockMvc.perform(get("/users"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstname", CoreMatchers.is("Laurent")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastname", CoreMatchers.is("GINA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email", CoreMatchers.is("laurentgina@mail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstname", CoreMatchers.is("Sophie")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].lastname", CoreMatchers.is("FONCEK")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].email", CoreMatchers.is("sophiefoncek@mail.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].firstname", CoreMatchers.is("Agathe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].lastname", CoreMatchers.is("FEELING")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].email", CoreMatchers.is("agathefeeling@mail.com")));

    }
}

/*
 * 
 * JSONArray jsonArray = JsonPath.read(json, "$.items.book[*]");
 * assertEquals(2, jsonArray.size());
 */