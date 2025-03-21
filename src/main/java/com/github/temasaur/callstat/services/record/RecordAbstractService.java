package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.repository.SubscriberRepository;

import java.util.ArrayList;
import java.util.List;

import com.github.temasaur.callstat.utils.RecordGenerator;
import com.github.temasaur.callstat.utils.TimeRange;
import com.github.temasaur.callstat.utils.UsageDataReportCreator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Реализация сервиса записей о звонках по умолчанию
 */
public abstract class RecordAbstractService implements RecordService {
	protected SubscriberRepository subscriberRepository;
	protected RecordGenerator recordGenerator;

	@Autowired
	public RecordAbstractService(
		SubscriberRepository subscriberRepository,
		RecordGenerator recordGenerator
	) {
		this.subscriberRepository = subscriberRepository;
		this.recordGenerator = recordGenerator;
	}

	@Override
	public List<Record> getAll() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Record> getBy(String msisdn) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Record> getByWithin(String msisdn, TimeRange timeRange) {
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

	@Override
	public UsageDataReport createUdrReport(String msisdn, String month) {
		assert msisdn != null;
		List<Record> records = month == null
				? getBy(msisdn)
				: getByWithin(msisdn, new TimeRange(month));

		return UsageDataReportCreator.create(msisdn, records);
	}

	@Override
	public List<UsageDataReport> createUdrReport(String month) {
		List<UsageDataReport> reports = new ArrayList<>();
		for (Subscriber subscriber : subscriberRepository.findAll()) {
			reports.add(createUdrReport(subscriber.msisdn, month));
		}
		return reports;
	}
}
