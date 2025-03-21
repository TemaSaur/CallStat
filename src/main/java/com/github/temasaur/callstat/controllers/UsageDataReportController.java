package com.github.temasaur.callstat.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.services.record.RecordService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsageDataReportController {
    private RecordService recordService;

    public UsageDataReportController() {}

    @Autowired
    public UsageDataReportController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/udr/{msisdn}")
    public UsageDataReport get(
            @PathVariable("msisdn") String msisdn,
            @RequestBody(required=false) GetUdrReportParams body
    ) {
        if (msisdn == null) {
            // throw error
        }
        String month = null;
        if (body != null) {
            month = body.month;
        }
        return recordService.createUdrReport(msisdn, month);
    }

    private static class GetUdrReportParams {
        @Schema(example="2024-05")
        @JsonProperty(required=false)
        public String month;
    }

}
