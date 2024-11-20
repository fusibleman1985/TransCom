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
import org.transcom.mappers.UserMapper;
import org.transcom.repositories.UserRepository;
import org.transcom.utils.ExpectedData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    private UUID expectedUserDtoResponseId;
    private UserDtoResponse expectedUserDtoResponse;
    private UserDtoRequest testUserDtoRequest;

    @BeforeEach
    void setUp() {
        testUserDtoRequest = ExpectedData.returnUserDtoRequest();
        expectedUserDtoResponse = ExpectedData.returnUserDtoResponse();
        expectedUserDtoResponseId = expectedUserDtoResponse.getId();
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
    public void testNegativeGetAllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseJsaonAsString = mvcResult.getResponse().getContentAsString();
        User[] actualUsers = objectMapper.readValue(responseJsaonAsString, User[].class);
        User[] expectedUsers = new User[0];

        assertNotEquals(expectedUsers.length, actualUsers.length);
    }

    @Test
    void testPositiveGetUserById() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", expectedUserDtoResponseId.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().contains(expectedUserDtoResponseId.toString()));
    }

    @Test
    void testNegativeGetUserById() throws Exception {
        String invalidUUID = UUID.randomUUID().toString();

        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/users/{id}", invalidUUID)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());
        assertFalse(mvcResult.getResponse().getContentAsString().contains(invalidUUID));
    }

    @Test
    public void testPositiveUpdateUser() throws Exception {
        testUserDtoRequest.setFirstName("UpdatedFirstName");
        testUserDtoRequest.setEmail("updated.email@example.com");

        expectedUserDtoResponse.setFirstName("UpdatedFirstName");
        expectedUserDtoResponse.setEmail("updated.email@example.com");

        String userUpdateRequestJson = objectMapper.writeValueAsString(testUserDtoRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", expectedUserDtoResponseId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateRequestJson))
//                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualUserDtoResponseJSON = mvcResult.getResponse().getContentAsString();
        UserDtoResponse actualUserDtoResponse = objectMapper.readValue(actualUserDtoResponseJSON, UserDtoResponse.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(expectedUserDtoResponse, actualUserDtoResponse);
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
                .andDo(print());
    }

    @Test
    public void testResponseOkDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", expectedUserDtoResponseId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testResponseNotFoundDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
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