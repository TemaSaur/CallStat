package com.github.temasaur.callstat.utils;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.UsageDataReport;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class UsageDataReportCreator {
    public static UsageDataReport create(String msisdn, List<Record> records) {
        Duration incoming = Duration.ZERO;
        Duration outcoming = Duration.ZERO;

        for (Record record : records) {
            if (record.initiator.msisdn.equals(msisdn)) {
                outcoming = outcoming.plus(Duration.between(record.callStart, record.callEnd));
            } else {
                incoming = incoming.plus(Duration.between(record.callStart, record.callEnd));
            }
        }
        return new UsageDataReport(msisdn, incoming, outcoming);
    }
}
