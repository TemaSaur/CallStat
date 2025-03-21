package com.github.temasaur.callstat.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeRange {
    public LocalDate start;
    public LocalDate end;

    public TimeRange(String month) {
        start = LocalDate.parse(month + "-01", DateTimeFormatter.ISO_LOCAL_DATE);
        end = start.plusMonths(1);
    }

    public boolean contains(LocalDateTime dateTime) {
        LocalDateTime startTime = start.atStartOfDay();
        LocalDateTime endTime = end.atStartOfDay();

        return dateTime.isAfter(startTime) && dateTime.isBefore(endTime);
    }

}
