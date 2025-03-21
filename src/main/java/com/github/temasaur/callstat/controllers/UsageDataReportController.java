package com.github.temasaur.callstat.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.services.record.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UsageDataReportController {
    private RecordService recordService;

    public UsageDataReportController() {}

    @Autowired
    public UsageDataReportController(RecordService recordService) {
        this.recordService = recordService;
    }

    @Operation(summary="Create UDR report for all subscribers")
    @PostMapping("/udr")
    public List<UsageDataReport> createAll(
            @RequestBody(required=false) GetUdrReportParams body
    ) {
        String month = null;
        if (body != null) {
            month = body.month;
        }
        return recordService.createUdrReport(month);
    }

    @Operation(summary="Create UDR report")
    @PostMapping("/udr/{msisdn}")
    public UsageDataReport create(
            @PathVariable("msisdn") String msisdn,
            @RequestBody(required=false) GetUdrReportParams body
    ) {
        assert msisdn != null;
        String month = null;
        if (body != null) {
            month = body.month;
        }
        return recordService.createUdrReport(msisdn, month);
    }

    public static class GetUdrReportParams {
        @Schema(example="2024-05")
        @JsonProperty(required=false)
        public String month;
    }

}
