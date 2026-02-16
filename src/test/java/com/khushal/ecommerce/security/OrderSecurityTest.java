package com.khushal.ecommerce.security;

import com.khushal.ecommerce.IntegrationTestBase;
import com.khushal.ecommerce.dto.OrderResponse;
import com.khushal.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderSecurityTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void updateStatus_forbiddenForUser() throws Exception {
        mockMvc.perform(post("/api/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"SHIPPED\"}")
                .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateStatus_allowedForAdmin() throws Exception {
        OrderResponse response = new OrderResponse(
                1L,
                "SHIPPED",
                BigDecimal.TEN,
                "123 Main St",
                LocalDateTime.now(),
                List.of()
        );
        when(orderService.updateOrderStatus(eq(1L), anyString())).thenReturn(response);

        mockMvc.perform(post("/api/orders/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"SHIPPED\"}")
                .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }
}
