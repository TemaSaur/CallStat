package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.utils.TimeRange;

import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;

/**
 * Сервис для управления записями о звонках
 */
public interface RecordService {
	List<Record> getAll();
	List<Record> getBy(String msisdn);
	List<Record> getByWithin(String msisdn, TimeRange timeRange);
	void set(List<Record> records);
	List<Record> generate(int maxRecordCount);
	UsageDataReport createUdrReport(String msisdn, String month);
	List<UsageDataReport> createUdrReport(String month);
	@Async
	void createCallDataRecordReport(String msisdn, TimeRange timeRange, UUID uuid);
}
