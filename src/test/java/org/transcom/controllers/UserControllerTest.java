package org.transcom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
import org.transcom.entities.User;
import org.transcom.mappers.UserMapper;
import org.transcom.repositories.UserRepository;
import org.transcom.utils.ExpectedData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private UUID expectedUserId;
    private User expectedUser;
    private UserDtoRequest expectedUserDtoRequest;

    @BeforeEach
    void setUp() {
        expectedUser = ExpectedData.returnUser();
        expectedUserId = expectedUser.getId();
        expectedUserDtoRequest = ExpectedData.returnUserDtoRequest();
    }

    @Test
    void testDatabaseInitialization() {
        long userCount = userRepository.count();
        System.out.println("Number of users in the database: " + userCount);
        assertEquals(2, userCount, "The number of users in the database is not as expected.");
    }

    @Test
    void testUserExistsInDatabase() {
        boolean userExists = userRepository.existsById(expectedUserId);
        assertTrue(userExists, "Expected user doesn't found in database.");
    }

    @Test
    @Transactional
    public void testResponseOk_CreateUser() throws Exception {
        String expectedUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedUserDtoRequestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testInvalidPassword_CreateUser() throws Exception {
        expectedUserDtoRequest.setPassword("Password");

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedInvalidUserDtoRequestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testInvalidUserStatus_CreateUser() throws Exception {
        expectedUserDtoRequest.setUserStatus(null);

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedInvalidUserDtoRequestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testInvalidLogin_CreateUser() throws Exception {
        expectedUserDtoRequest.setLogin("");

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        checkForExpectedResultByCreateUser(expectedInvalidUserDtoRequestJson);
    }

    @Test
    public void testInvalidFirstName_CreateUser() throws Exception {
        expectedUserDtoRequest.setFirstName("");

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        checkForExpectedResultByCreateUser(expectedInvalidUserDtoRequestJson);
    }

    @Test
    public void testInvalidLastName_CreateUser() throws Exception {
        expectedUserDtoRequest.setLastName("");

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        checkForExpectedResultByCreateUser(expectedInvalidUserDtoRequestJson);
    }

    @Test
    public void testInvalidEmail_CreateUser() throws Exception {
        expectedUserDtoRequest.setEmail("invalidemail");

        String expectedInvalidUserDtoRequestJson = objectMapper.writeValueAsString(expectedUserDtoRequest);

        checkForExpectedResultByCreateUser(expectedInvalidUserDtoRequestJson);
    }

    private void checkForExpectedResultByCreateUser(String expectedInvalidUserDtoRequestJson) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedInvalidUserDtoRequestJson))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void testGetAllUsers() throws Exception {
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

//    @Test
//    void testEqualsUserGetUserById() throws Exception {
//        MvcResult mvcResult =
//                mockMvc.perform(MockMvcRequestBuilders
//                                .get("/users/{id}", expectedUserId.toString())
//                                .contentType(MediaType.APPLICATION_JSON))
//                        .andReturn();
//
//        String actualUserJSON = mvcResult.getResponse().getContentAsString();
//        User actualUser = objectMapper.readValue(actualUserJSON, User.class);
//        assertEquals(expectedUser, actualUser);
//    }
//    @Test
//    public void testUpdateUser() throws Exception {
//        User updatedUser = new User();
//        updatedUser.setName("Updated Name");
//        updatedUser.setEmail("updated.email@example.com");
//
//        String userJson = objectMapper.writeValueAsString(updatedUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", expectedUserId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isOk())
//                .andDo(print());

//    }
//    @Test
//    public void testUpdateNonExistentUser() throws Exception {
//        UUID nonExistentUserId = UUID.randomUUID();
//
//        User updatedUser = new User();
//        updatedUser.setName("Updated Name");
//        updatedUser.setEmail("updated.email@example.com");
//
//        String userJson = objectMapper.writeValueAsString(updatedUser);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", nonExistentUserId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isNotFound())
//                .andDo(print());

//    }

    @Test
    void testResponseStatusOkGetUserById() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", expectedUserId.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        int statusOk = mvcResult.getResponse().getStatus();
        assertEquals(200, statusOk);
    }

    @Test
    public void testResponseOkDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", expectedUserId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testResponseNotFoundDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testInvalidIdFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "invalid-uuid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
//    @Test
//    public void testFilterUsersByName() throws Exception {
//    String methodName = new Throwable().getStackTrace()[0].getMethodName();
//    System.out.println(">>> Start test: " + methodName);
//
//    mockMvc.perform(MockMvcRequestBuilders.get("/users?name=John")
//                    .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andDo(print());
//    }
//
//    @Test
//    public void testPagination() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void testUnauthorizedAccess() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users")
//                        .header("Authorization", "InvalidToken"))
//                .andExpect(status().isUnauthorized())
//                .andDo(print());
//    }
//
//    @Test
//    public void testHandleException() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "invalid-id"))
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
//                .andDo(print());
//    }

}