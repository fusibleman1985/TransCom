package org.transcom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.entities.User;
import org.transcom.entities.enums.UserStatus;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.exceptions.enums.ErrorMessages;
import org.transcom.mappers.UserMapper;
import org.transcom.repositories.UserRepository;
import org.transcom.utils.ExpectedData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    private UserDtoResponse expectedUserDtoResponse;
    private UUID expectedUserId;
    private User expectedUser;
    private UserDtoRequest testUserDtoRequest;

    @BeforeEach
    void setUp() {
        testUserDtoRequest = ExpectedData.returnUserDtoRequest();
        expectedUserDtoResponse = ExpectedData.returnUserDtoResponse();
        expectedUser = ExpectedData.returnUser();
        expectedUserId = ExpectedData.returnUser().getId();
    }

    @Test
    public void testPositiveRegisterUser() throws Exception {
        String testUserDtoRequestJson = objectMapper.writeValueAsString(testUserDtoRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserDtoRequestJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String actualUserDtoResponseJSON = mvcResult.getResponse().getContentAsString();
        UserDtoResponse actualUserDtoResponse = objectMapper.readValue(actualUserDtoResponseJSON, UserDtoResponse.class);

        actualUserDtoResponse.setId(null);
        expectedUserDtoResponse.setId(null);
        expectedUserDtoResponse.setUserStatus(UserStatus.BLOCKED);

        assertEquals(expectedUserDtoResponse, actualUserDtoResponse);
    }

    @Test
    public void testNegativeRegisterUser() throws Exception {
        testUserDtoRequest.setLogin("");
        String invalidUserDtoRequestJson = objectMapper.writeValueAsString(testUserDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserDtoRequestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testPositiveGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    User[] users = objectMapper.readValue(content, User[].class);
                    assertTrue(users.length > 0, "The list of users must be non-empty.");
                })
                .andDo(print());
    }

    @Test
    void testNegativeGetAllUsers() throws Exception {
        userRepository.deleteAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andDo(print());
    }

    @Test
    void testPositiveGetUserById() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", expectedUserId.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().contains(expectedUserId.toString()));
    }

    @Test
    void testNegativeGetUserById() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                        .andExpect(result ->
                                assertEquals(ErrorMessages.USER_NOT_FOUND.getMessage(), result.getResolvedException().getMessage()))
                        .andDo(print())
                        .andReturn();
    }

    @Test
    public void testUpdateUser() throws Exception {
        testUserDtoRequest.setFirstName("UpdatedFirstName");
        testUserDtoRequest.setEmail("updated.email@example.com");

        expectedUser.setFirstName("UpdatedFirstName");
        expectedUser.setEmail("updated.email@example.com");

        String userUpdateRequestJson = objectMapper.writeValueAsString(testUserDtoRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", expectedUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateRequestJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualUserJSON = mvcResult.getResponse().getContentAsString();
        User actualUser = objectMapper.readValue(actualUserJSON, User.class);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testUpdateNonExistentUser() throws Exception {
        testUserDtoRequest.setFirstName("UpdatedFirstName");
        testUserDtoRequest.setEmail("updated.email@example.com");

        String userUpdateRequestJson = objectMapper.writeValueAsString(testUserDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateRequestJson))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result ->
                        assertEquals(ErrorMessages.USER_NOT_FOUND.getMessage(), result.getResolvedException().getMessage()))
                .andDo(print());
    }

    @Test
    public void testResponseOkDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", expectedUserId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testResponseNotFoundDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}