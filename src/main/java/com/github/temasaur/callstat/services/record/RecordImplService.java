package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.repository.RecordRepository;
import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.RecordGenerator;
import com.github.temasaur.callstat.utils.TimeRange;
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
			SubscriberService subscriberService,
			RecordRepository recordRepository,
			RecordGenerator recordGenerator,
			BackgroundTaskService backgroundTaskService
	) {
		super(subscriberService, recordGenerator, backgroundTaskService);
		this.recordRepository = recordRepository;
	}

	@Override
	public List<Record> getBy(String msisdn) {
		return (List<Record>) recordRepository.findByInitiator_MsisdnOrRecipient_MsisdnOrderByCallStartAsc(msisdn, msisdn);
	}

	@Override
	public List<Record> getByWithin(String msisdn, TimeRange timeRange) {
		return (List<Record>) recordRepository.findBySubscriberWithin(msisdn, timeRange.startTime(), timeRange.endTime());
	}

	@Override
	public List<Record> getAll() {
		return (List<Record>) recordRepository.findAll();
	}

	@Override
	public void set(List<Record> records) {
		recordRepository.deleteAll();
		recordRepository.saveAll(records);
	}
}