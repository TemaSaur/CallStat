package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.services.subscriber.SubscriberMockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriberController.class)
@Import(SubscriberMockService.class)
public class SubscriberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/subscribers")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void shouldGenerateAndReturn() throws Exception {
         mockMvc.perform(post("/subscribers/generate"))
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$").isArray())
                 .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void shouldReturnAfterGenerating() throws Exception {
        mockMvc.perform(post("/subscribers/generate")).andReturn();
        mockMvc.perform(get("/subscribers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void shouldErrorWhenGenerating1Subscriber() throws Exception {
        mockMvc.perform(post("/subscribers/generate")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"subscriberCount\": 1}"))
            .andExpect(status().isBadRequest());
    }
}
