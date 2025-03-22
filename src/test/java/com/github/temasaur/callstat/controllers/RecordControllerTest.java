package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.services.record.RecordMockService;
import com.github.temasaur.callstat.services.subscriber.SubscriberMockService;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecordController.class)
@Import({SubscriberMockService.class, RecordMockService.class, RecordGenerator.class, SubscriberController.class})
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/records")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }


    @Test
    void shouldErrorWithNoSubscribers() throws Exception {
        mockMvc.perform(post("/records/generate"))
                .andExpect(status().isPreconditionRequired());
    }

   @Nested
   class WithSubscribers {
        @BeforeEach
        public void setUp() throws Exception {
            mockMvc.perform(post("/subscribers/generate")).andReturn();
        }

        @Test
        void shouldGenerateAndReturn() throws Exception {
            mockMvc.perform(post("/records/generate"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isNotEmpty());
        }

        @Test
        void shouldReturnAfterGenerating() throws Exception {
            mockMvc.perform(post("/subscribers/generate")).andReturn();
            mockMvc.perform(post("/records/generate")).andReturn();
            mockMvc.perform(get("/records"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isNotEmpty());
        }
   }
}
