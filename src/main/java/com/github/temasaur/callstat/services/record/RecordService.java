package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;

import java.util.List;

/**
 * Сервис для управления записями о звонках
 */
public interface RecordService {
	List<Record> getAll();
	void set(List<Record> records);
	List<Record> generate(int maxRecordCount);
}
