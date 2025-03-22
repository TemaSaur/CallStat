package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.RecordGenerator;
import com.github.temasaur.callstat.utils.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.temasaur.callstat.models.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса записей о звонках на списке
 */
@Service
public class RecordMockService extends RecordAbstractService {
	private List<Record> records;

	@Autowired
	public RecordMockService(
		SubscriberService subscriberService,
		RecordGenerator recordGenerator,
		BackgroundTaskService backgroundTaskService
	) {
		super(subscriberService, recordGenerator, backgroundTaskService);
	}

	@Override
	public List<Record> getBy(String msisdn) {
		ArrayList<Record> result = new ArrayList<>();
		for (Record record : records) {
			if (record.initiator.msisdn.equals(msisdn) || record.recipient.msisdn.equals(msisdn)) {
				result.add(record);
			}
		}
		return result;
	}

	@Override
	public List<Record> getByWithin(String msisdn, TimeRange range) {
		ArrayList<Record> result = new ArrayList<>();
		for (Record record : records) {
			if (
				(record.initiator.msisdn.equals(msisdn) || record.recipient.msisdn.equals(msisdn))
				&& range.contains(record.callStart)
			) {
				result.add(record);
			}
		}
		return result;
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