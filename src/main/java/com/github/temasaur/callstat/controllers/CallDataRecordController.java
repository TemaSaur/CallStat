package com.github.temasaur.callstat.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.temasaur.callstat.models.BackgroundTask;
import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskService;
import com.github.temasaur.callstat.services.record.RecordService;
import com.github.temasaur.callstat.types.ErrorResponse;
import com.github.temasaur.callstat.utils.TimeRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@Tag(name="CDR Reports")
public class CallDataRecordController {
    RecordService recordService;
    BackgroundTaskService backgroundTaskService;

    @Autowired
    public CallDataRecordController(
        RecordService recordService,
        BackgroundTaskService backgroundTaskService
    ) {
        this.recordService = recordService;
        this.backgroundTaskService = backgroundTaskService;
    }

    @Operation(summary="Create CDR report")
    @PostMapping("/cdr/start")
    public GetCdrReportResponse getCdrReport(
            @RequestBody(required=true) GetCdrReportParams params
    ) {
        TimeRange timeRange = new TimeRange(params.startDate, params.endDate);

        UUID uuid = UUID.randomUUID();

        recordService.createCallDataRecordReport(params.msisdn, timeRange, uuid);

        return new GetCdrReportResponse(uuid);
    }

    /**
     * Получить статус CDR отчета
     * @param uuid UUID отчета
     * @return статус отчета
     */
	@Operation(summary="Get CDR report status", responses={
		@ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=BackgroundTask.class)), description="Report status found"),
		@ApiResponse(responseCode="428", content=@Content(schema=@Schema(implementation=ErrorResponse.class)), description="Report not found")
	})
    @GetMapping("/cdr/status/{uuid}")
    public ResponseEntity<?> getCdrReportStatus(@PathVariable UUID uuid) {
        BackgroundTask task = backgroundTaskService.getState(uuid);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "No task found"));
        }
        return ResponseEntity.ok(task);
    }

    public static class GetCdrReportParams {
        @Schema(example="79123456789")
        @JsonProperty(required=true)
        public String msisdn;

        @Schema(example="2024-05-01")
        @JsonProperty(required=true)
        public LocalDate startDate;

        @Schema(example="2024-05-01")
        @JsonProperty(required=true)
        public LocalDate endDate;
    }

    public static class GetCdrReportResponse {
        public UUID uuid;

        public GetCdrReportResponse(UUID uuid) {
            this.uuid = uuid;
        }
    }
}
