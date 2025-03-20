package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.repository.SubscriberRepository;

import java.util.List;

import com.github.temasaur.callstat.utils.RecordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class RecordServiceAbstractService implements RecordService {

	protected SubscriberRepository subscriberRepository;
	protected RecordGenerator recordGenerator;

	public RecordServiceAbstractService() {}

	@Autowired
	public RecordServiceAbstractService(
		SubscriberRepository subscriberRepository,
		RecordGenerator recordGenerator
	) {
		this.subscriberRepository = subscriberRepository;
		this.recordGenerator = recordGenerator;
	}

	@Override
	public List<Record> get() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void set(List<Record> records) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Record> generate(int maxRecordCount) {
		if (subscriberRepository.count() == 0) {
			throw new IllegalStateException("Can't generate records. No subscribers found");
		}
		return recordGenerator.generate(maxRecordCount);
	}
}
