package com.github.temasaur.callstat.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Random;

/**
 * Генератор записей о звонках
 */
@Component
public class RecordGenerator {
    private final SubscriberService subscriberService;

    @Autowired
    public RecordGenerator(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

	/**
	 * Generate a list of records with random subscribers, start time, and end time
	 * @return A list of records
	 */
    public List<Record> generate(int maxCalls) {
        List<Subscriber> subscribers = subscriberService.getAll();

		assert subscribers != null && !subscribers.isEmpty();

		// start at a point a year ago.
		LocalDateTime current = LocalDateTime.now().minusYears(1);
		LocalDateTime now =	LocalDateTime.now();

		List<Record> records = new ArrayList<>();
		// repeat until `maxCalls` records are created or the time is present

		while ((maxCalls < 0 || (records.size() < maxCalls)) && current.isBefore(now)) {
			current = waitBetween(current, Duration.ofHours(1), Duration.ofHours(12));

			Record record = generateRecord(current, subscribers);

			records.add(record);
			current = record.callEnd;
		}

        return records;
    }

	/**
	 * Generate a record with a random subscriber, start time, and end time
	 * @param current The current time
	 * @param subscribers The list of subscribers
	 * @return A record with a random subscriber, start time, and end time
	 */
	private Record generateRecord(LocalDateTime current, List<Subscriber> subscribers) {
		Random random = new Random();

		Subscriber subscriber1 = subscribers.get(random.nextInt(subscribers.size()));
		Subscriber subscriber2 = subscribers.get(random.nextInt(subscribers.size()));

		while (subscriber1.equals(subscriber2)) {
			subscriber2 = subscribers.get(random.nextInt(subscribers.size()));
		}

		LocalDateTime end = waitBetween(current, Duration.ofMinutes(1), Duration.ofMinutes(10));

		return new Record(subscriber1, subscriber2, current, end);
	}

	private LocalDateTime waitBetween(LocalDateTime current, Duration min, Duration max) {
		Duration waitFor = randomDuration(min, max);
		return current.plus(waitFor);
	}

	private Duration randomDuration(Duration min, Duration max) {
		Random random = new Random();
		long minSeconds = min.getSeconds();
		long maxSeconds = max.getSeconds();
		long randomSeconds = random.nextLong(minSeconds, maxSeconds);
		return Duration.ofSeconds(randomSeconds);
	}
}
