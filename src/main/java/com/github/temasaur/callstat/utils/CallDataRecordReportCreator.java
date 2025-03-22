package com.github.temasaur.callstat.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.temasaur.callstat.models.CallDataRecord;
import com.github.temasaur.callstat.models.Record;

public class CallDataRecordReportCreator {
	public static List<CallDataRecord> create(String msisdn, List<Record> records) {
		List<CallDataRecord> cdr = new ArrayList<>();
		for (Record record : records) {
			cdr.add(new CallDataRecord(record, msisdn));
		}
		return cdr;
	}
}
