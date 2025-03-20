package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.repository.SubscriberRepository;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.github.temasaur.callstat.models.Record;

import java.util.List;

@Service
@Primary
public class RecordServiceMockService extends RecordServiceAbstractService {
	private List<Record> records;

	public RecordServiceMockService(
		SubscriberRepository subscriberRepository,
		RecordGenerator recordGenerator
	) {
		super(subscriberRepository, recordGenerator);
	}

	@Override
	public List<Record> get() {
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