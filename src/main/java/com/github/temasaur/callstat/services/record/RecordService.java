package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;

import java.util.List;

public interface RecordService {
	List<Record> get();
	void set(List<Record> records);
	List<Record> generate();
}
