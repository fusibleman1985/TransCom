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
import org.transcom.dto.OrderDtoRequest;
import org.transcom.entities.Order;
import org.transcom.entities.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrderPositiveTest() throws Exception {
        OrderDtoRequest orderDtoRequest = getOrderDtoRequest();
        String json = objectMapper.writeValueAsString(orderDtoRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/orders/saveOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Order actualOrder = objectMapper.readValue(jsonResult, Order.class);
        Order expectedOrder = getOrder();
        expectedOrder.setId(actualOrder.getId());

        Assertions.assertEquals(expectedOrder, actualOrder);
        Assertions.assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void createOrderNegativeTest() throws Exception {
        OrderDtoRequest orderDtoRequest = getOrderDtoRequest();
        orderDtoRequest.setUserId(UUID.randomUUID());
        String json = objectMapper.writeValueAsString(orderDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/saveOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print()).andReturn();
    }

    @Test
    void createOrderWithInvalidWeightNegativeTest() throws Exception {
        OrderDtoRequest orderDtoRequest = getOrderDtoRequest();
        orderDtoRequest.setWeight(0);
        String json = objectMapper.writeValueAsString(orderDtoRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/saveOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print()).andReturn();
    }

    @Test
    void findOrderByIdPositiveTest() throws Exception {
        UUID id = UUID.fromString("333e4567-e89b-12d3-a456-426614174000");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/findById/{id}",
                                id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        Assertions.assertTrue(jsonResult.contains(id.toString()));
    }

    @Test
    void findOrderByIdNegativeTest() throws Exception {
        UUID id = UUID.fromString("333e4567-e89b-12d3-a456-426614174001");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/findById/{id}",
                                id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        Assertions.assertFalse(jsonResult.contains(id.toString()));
    }

    @Test
    void testPositiveGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    Order[] orders = objectMapper.readValue(content, Order[].class);
                    assertTrue(orders.length > 0, "The list of orders must be non-empty.");
                })
                .andDo(print());
    }

    @Test
    void updateOrderPositiveTest() throws Exception {
        OrderDtoRequest orderDtoRequest = getOrderDtoRequest();
        orderDtoRequest.setPrice(BigDecimal.valueOf(10000));

        Order expectedOrder = getOrder();
        expectedOrder.setPrice(BigDecimal.valueOf(10000));
        expectedOrder.setId(UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));

        String json = objectMapper.writeValueAsString(orderDtoRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/orders/update/{id}", expectedOrder.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        Order actualOrder = objectMapper.readValue(jsonResult, Order.class);

        Assertions.assertEquals(expectedOrder, actualOrder);
        Assertions.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void updateOrderNegativeTest() throws Exception {
        OrderDtoRequest orderDtoRequest = getOrderDtoRequest();
        orderDtoRequest.setWeight(500);

        String invalidOrderId = "10000000-7777-7777-7777-000000000001";

        String json = objectMapper.writeValueAsString(orderDtoRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/orders/update/{id}", invalidOrderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn();

        Assertions.assertEquals(404, result.getResponse().getStatus());

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("Order not found"));
    }

    @Test
    void deleteOrderPositiveTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/delete/{id}",
                                "333e4567-e89b-12d3-a456-426614174000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteOrderNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/delete/{id}",
                                "333e4567-e89b-12d3-a456-426614174001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private OrderDtoRequest getOrderDtoRequest() {
        OrderDtoRequest orderDtoRequest = new OrderDtoRequest();
        orderDtoRequest.setUserId(UUID.fromString("222e4567-e89b-12d3-a456-426614174000"));
        orderDtoRequest.setWeight(89);
        orderDtoRequest.setPrice(BigDecimal.valueOf(122.0));
        orderDtoRequest.setDescription("description");
        orderDtoRequest.setOrderStatus(OrderStatus.valueOf("CREATED"));
        return orderDtoRequest;
    }

    private Order getOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setWeight(89);
        order.setPrice(BigDecimal.valueOf(122.0));
        order.setDescription("description");
        order.setOrderStatus(OrderStatus.valueOf("CREATED"));
        return order;
    }
}