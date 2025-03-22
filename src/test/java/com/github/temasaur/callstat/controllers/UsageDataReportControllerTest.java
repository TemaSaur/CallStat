package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskMockService;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UsageDataReportController.class)
@Import({SubscriberMockService.class, RecordMockService.class, BackgroundTaskMockService.class, RecordGenerator.class, SubscriberController.class, RecordController.class})
public class UsageDataReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/udr")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Nested
    class WithPrerequisites {
        @BeforeEach
        public void setUp() throws Exception {
            mockMvc.perform(post("/subscribers/generate")).andReturn();
            mockMvc.perform(post("/records/generate")).andReturn();
        }

        @Test
        public void shouldGenerateSubscriberMonth() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/subscribers")).andReturn();
            String msisdn = mvcResult.getResponse().getContentAsString().split("\"")[3];

            mockMvc.perform(post("/udr/" + msisdn)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"month\": \"" + getMonth() + "\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$").isNotEmpty())
                    .andExpect(jsonPath("$.msisdn").value(msisdn));
        }

        @Test
        public void shouldGenerateSubscriberAll() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/subscribers")).andReturn();
            String msisdn = mvcResult.getResponse().getContentAsString().split("\"")[3];

            mockMvc.perform(post("/udr/" + msisdn))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isMap())
                    .andExpect(jsonPath("$").isNotEmpty())
                    .andExpect(jsonPath("$.msisdn").value(msisdn));
        }

        @Test
        public void shouldGenerateAllMonth() throws Exception {
            mockMvc.perform(post("/udr")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"month\": \"" + getMonth() + "\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isNotEmpty());
        }

        @Test
        public void shouldGenerateAllAll() throws Exception {
            mockMvc.perform(post("/udr"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isNotEmpty());
        }

        private String getMonth() {
            LocalDate earlier = LocalDate.now().minusMonths(2);

            return String.format("%04d-%02d", earlier.getYear(), earlier.getMonthValue());
        }
    }
}
