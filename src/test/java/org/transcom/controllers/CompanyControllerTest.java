package org.transcom.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.dto.CompanyDtoResponse;
import org.transcom.entities.enums.ClientStatus;
import org.transcom.entities.enums.CompanyRole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/schemaCompany.sql", "/db/dataCompany.sql"})
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCompanyPositiveTest() throws Exception {
        CompanyDtoRequest companyDtoRequest = getCompanyDtoRequest();
        String json = objectMapper.writeValueAsString(companyDtoRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/companies/create").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        CompanyDtoResponse actualOrder = objectMapper.readValue(jsonResult, CompanyDtoResponse.class);

        CompanyDtoResponse companyDtoResponse = getCompanyDtoResponse();
        Assertions.assertEquals(companyDtoResponse, actualOrder);
        Assertions.assertEquals(201, result.getResponse().getStatus());
    }


    @Test
    void createCompanyNegativeTest() throws Exception {
        CompanyDtoRequest companyDtoRequest = getCompanyDtoRequest();
        companyDtoRequest.setRating(110);
        String json = objectMapper.writeValueAsString(companyDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/companies/create").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andExpect(jsonPath("$.rating").value("must be less than or equal to 100")).andDo(print()).andReturn();
    }

//    @Test
//    void findCompanyByIdPositiveTest() throws Exception {
//
//        // Создаем mock данные для CompanyDtoResponse
//        CompanyDtoRequest companyDtoRequest = getCompanyDtoRequest();
//        CompanyDtoResponse companyDtoResponse = new CompanyDtoResponse();
//        companyDtoResponse.setCompanyName(companyDtoRequest.getCompanyName());
//        companyDtoResponse.setCompanyRole(companyDtoRequest.getCompanyRole());
//        companyDtoResponse.setLicenseId(companyDtoRequest.getLicenseId());
//        companyDtoResponse.setRating(companyDtoRequest.getRating());
//        companyDtoResponse.setCompanyStatus(companyDtoRequest.getCompanyStatus());
//
//        // Добавляем пользователей для поля usersFullNameAndId
//        List<String> users = new ArrayList<>();
//        users.add("Test User 1");
//        users.add("Test User 2");
//        companyDtoResponse.setUsersFullNameAndId(users);
//
//        // Настройте mockMvc для возврата нужного ответа
//        when(companyService.findCompanyById(1L)).thenReturn(companyDtoResponse);
//        Long id = 1L;
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/companies/get/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andReturn();
//
//        String jsonResult = result.getResponse().getContentAsString();
//        Assertions.assertTrue(jsonResult.contains("Test User 1"));
//        Assertions.assertTrue(jsonResult.contains("Test User 2"));
//    }

    @Test
    void findCompanyByIdPositiveTest() throws Exception {
        Long companyId = 1L;
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/companies/get/{id}", companyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
    }

    private CompanyDtoRequest getCompanyDtoRequest() {
        List<UUID> users = new ArrayList<>();
//        users.add(UUID.randomUUID());
        CompanyDtoRequest companyDtoRequest = new CompanyDtoRequest();
        companyDtoRequest.setCompanyName("Google");
        companyDtoRequest.setCompanyRole(CompanyRole.valueOf("BROKER"));
        companyDtoRequest.setLicenseId("212");
        companyDtoRequest.setRating(60);
        companyDtoRequest.setCompanyStatus(ClientStatus.valueOf("ACTIVE"));
        companyDtoRequest.setUserIds(users);
        return companyDtoRequest;
    }


    private CompanyDtoResponse getCompanyDtoResponse() {
        List<String> users = new ArrayList<>();
//        users.add("Example User");
        CompanyDtoResponse companyDtoResponse = new CompanyDtoResponse();
        companyDtoResponse.setCompanyName("Google");
        companyDtoResponse.setCompanyRole(CompanyRole.valueOf("BROKER"));
        companyDtoResponse.setLicenseId("212");
        companyDtoResponse.setRating(60);
        companyDtoResponse.setCompanyStatus(ClientStatus.valueOf("ACTIVE"));
        companyDtoResponse.setUsersFullNameAndId(users);
        return companyDtoResponse;
    }
}