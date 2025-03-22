package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskMockService;
import com.github.temasaur.callstat.services.record.RecordMockService;
import com.github.temasaur.callstat.services.subscriber.SubscriberMockService;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CallDataRecordController.class)
@Import({SubscriberMockService.class, RecordMockService.class, BackgroundTaskMockService.class, RecordGenerator.class, SubscriberController.class, RecordController.class})
public class CallDataRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFailWithNoParams() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/cdr/start")).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Nested
    class WithPrerequisites {
        private String msisdn;
        private UUID taskId;
        private File reportsDir;
        private Set<String> testFiles;

        @BeforeEach
        public void setUp() throws Exception {
            // Use existing reports directory
            reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            testFiles = new HashSet<>();

            // Set up test data
            mockMvc.perform(post("/subscribers/generate")).andReturn();
            mockMvc.perform(post("/records/generate")).andReturn();

            MvcResult mvcResult = mockMvc.perform(get("/subscribers")).andReturn();
            msisdn = mvcResult.getResponse().getContentAsString().split("\"")[3];

            // Start a CDR generation task
            mvcResult = mockMvc.perform(post("/cdr/start")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format(
                        "{\"msisdn\": \"%s\", \"startDate\": \"%s\", \"endDate\": \"%s\"}",
                        msisdn,
                        getStartDate(),
                        getEndDate()
                    )))
                    .andReturn();

            taskId = UUID.fromString(mvcResult.getResponse().getContentAsString().split("\"")[3]);
            // Track the file that will be created
            testFiles.add(msisdn + "_" + taskId.toString() + ".csv");
        }

        @AfterEach
        public void cleanup() {
            // Only delete files created during this test
            for (String filename : testFiles) {
                File file = new File(reportsDir, filename);
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        @Test
        void shouldStartCdrGeneration() throws Exception {
            MvcResult mvcResult = mockMvc.perform(post("/cdr/start")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.format(
                        "{\"msisdn\": \"%s\", \"startDate\": \"%s\", \"endDate\": \"%s\"}",
                        msisdn,
                        getStartDate(),
                        getEndDate()
                    )))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.uuid").exists())
                    .andReturn();

            // Track the new file that will be created
            UUID newTaskId = UUID.fromString(mvcResult.getResponse().getContentAsString().split("\"")[3]);
            testFiles.add(msisdn + "_" + newTaskId.toString() + ".csv");
        }

        @Test
        void shouldCheckTaskStatus() throws Exception {
            // Wait a bit for the file to be created
            Thread.sleep(100);
            
            mockMvc.perform(get("/cdr/status/" + taskId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.status").exists())
                    .andExpect(jsonPath("$.message").exists());

            // Verify that the file was created
            File expectedFile = new File(reportsDir, msisdn + "_" + taskId.toString() + ".csv");
            assertTrue(expectedFile.exists(), "CDR report file should be created");
        }

        @Test
        void shouldFailWithInvalidTaskId() throws Exception {
            UUID invalidTaskId = UUID.randomUUID();
            mockMvc.perform(get("/cdr/status/" + invalidTaskId))
                    .andExpect(status().isBadRequest());
        }

        private String getStartDate() {
            LocalDate earlier = LocalDate.now().minusMonths(2);
            return earlier.toString();
        }

        private String getEndDate() {
            LocalDate earlier = LocalDate.now().minusMonths(1);
            return earlier.toString();
        }
    }
}
