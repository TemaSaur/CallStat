package com.github.temasaur.callstat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.github.temasaur.callstat.types.CsvStringer;

public class CallDataRecord implements CsvStringer {
    private final String subject;
    private final Record self;

    public CallDataRecord(Record record, String msisdn) {
        self = record;
        subject = msisdn;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (Objects.equals(subject, self.initiator.msisdn)) {
            sb.append("01,");
        } else {
            sb.append("02,");
        }

        sb.append(self.initiator.msisdn);
        sb.append(",");

        sb.append(self.recipient.msisdn);
        sb.append(",");

        sb.append(formatDateTime(self.callStart));
        sb.append(",");

        sb.append(formatDateTime(self.callEnd));

        return sb.toString();
    }

    public static String getCsvHeader() {
        return "type,initiator,recipient,start,end";
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return dateTime.format(formatter);
    }
}
