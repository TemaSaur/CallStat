package com.github.temasaur.callstat.services.record;

import com.github.temasaur.callstat.models.BackgroundTask;
import com.github.temasaur.callstat.models.CallDataRecord;
import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.types.CsvStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;

import com.github.temasaur.callstat.services.backgroundTask.BackgroundTaskService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.CallDataRecordReportCreator;
import com.github.temasaur.callstat.utils.CsvWriter;
import com.github.temasaur.callstat.utils.RecordGenerator;
import com.github.temasaur.callstat.utils.TimeRange;
import com.github.temasaur.callstat.utils.UsageDataReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * Реализация сервиса записей о звонках по умолчанию
 */
public abstract class RecordAbstractService implements RecordService {
	protected SubscriberService subscriberService;
	protected RecordGenerator recordGenerator;
	protected BackgroundTaskService backgroundTaskService;

	@Autowired
	public RecordAbstractService(
		SubscriberService subscriberService,
		RecordGenerator recordGenerator,
		BackgroundTaskService backgroundTaskService
	) {
		this.subscriberService = subscriberService;
		this.recordGenerator = recordGenerator;
		this.backgroundTaskService = backgroundTaskService;
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
		if (subscriberService.isEmpty()) {
			throw new IllegalStateException("Can't generate records. No subscribers found");
		}
		return recordGenerator.generate(maxRecordCount, subscriberService.getAll());
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
		for (Subscriber subscriber : subscriberService.getAll()) {
			reports.add(createUdrReport(subscriber.msisdn, month));
		}
		return reports;
	}

	@Override
	@Async
	public void createCallDataRecordReport(String msisdn, TimeRange timeRange, UUID uuid) {
		CompletableFuture.runAsync(() -> {
			backgroundTaskService.setState(uuid, new BackgroundTask(uuid));

			List<Record> records = getByWithin(msisdn, timeRange);
			List<CallDataRecord> cdr = CallDataRecordReportCreator.create(msisdn, records);
			String filename = "reports/" + msisdn + "_" + uuid.toString() + ".csv";

			try {
				CsvWriter.write(filename, CallDataRecord.getCsvHeader(), cdr.stream().map(r -> (CsvStringer)r).toList());
				backgroundTaskService.setStatusMessage(uuid, BackgroundTask.Status.FINISHED, "Report created successfully");
			} catch (IOException | InterruptedException e) {
				backgroundTaskService.setStatusMessage(uuid, BackgroundTask.Status.FAILED, e.getMessage());
			}
		});
	}
}
