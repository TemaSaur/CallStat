package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.repository.RecordRepository;
import com.github.temasaur.callstat.repository.SubscriberRepository;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class RecordImplService extends RecordAbstractService {
	private final RecordRepository recordRepository;

	@Autowired
	public RecordImplService(
			SubscriberRepository subscriberRepository,
			RecordRepository recordRepository,
			RecordGenerator recordGenerator
	) {
		super(subscriberRepository, recordGenerator);
		this.recordRepository = recordRepository;
	}

	@Override
	public List<Record> getAll() {
		List<Record> records = new ArrayList<>();
		for (Record record : recordRepository.findAll()) {
			records.add(record);
		}
		return records;
	}

	@Override
	public void set(List<Record> records) {
		recordRepository.saveAll(records);
	}
}