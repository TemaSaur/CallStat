package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.repository.RecordRepository;
import com.github.temasaur.callstat.repository.SubscriberRepository;
import com.github.temasaur.callstat.utils.RecordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса записей о звонках на базе данных
 */
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
		return (List<Record>) recordRepository.findAll();
	}

	@Override
	public void set(List<Record> records) {
		recordRepository.saveAll(records);
	}
}