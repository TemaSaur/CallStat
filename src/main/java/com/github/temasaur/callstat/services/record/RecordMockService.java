package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.repository.SubscriberRepository;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.springframework.stereotype.Service;
import com.github.temasaur.callstat.models.Record;

import java.util.List;

/**
 * Реализация сервиса записей о звонках на списке
 */
@Service
public class RecordMockService extends RecordAbstractService {
	private List<Record> records;

	public RecordMockService(
		SubscriberRepository subscriberRepository,
		RecordGenerator recordGenerator
	) {
		super(subscriberRepository, recordGenerator);
	}

	@Override
	public List<Record> getAll() {
		if (records == null) {
			return List.of();
		}
		return records;
	}

	@Override
	public void set(List<Record> records) {
		this.records = records;
	}
}