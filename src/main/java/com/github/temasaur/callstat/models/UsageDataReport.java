package com.github.temasaur.callstat.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;

public class UsageDataReport {
    public String msisdn;
    public CallData incomingCall;
    public CallData outcomingCall;

    public static class CallData {
//        private Duration duration;
        @Schema(example="00:02:50")
        public String totalTime;

        public CallData(Duration duration) {
//            this.duration = duration;
            int hours = duration.toHoursPart();
            int minutes = duration.toMinutesPart() % 60;
            int seconds = duration.toSecondsPart() % 60;
            this.totalTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }

    public UsageDataReport(String msisdn, Duration incomingDuration, Duration outcomingDuration) {
        this.msisdn = msisdn;
        this.incomingCall = new CallData(incomingDuration);
        this.outcomingCall = new CallData(outcomingDuration);
    }
}
