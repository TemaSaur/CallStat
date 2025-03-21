package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.models.UsageDataReport;
import com.github.temasaur.callstat.services.record.RecordService;
import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.TimeRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
public abstract class RecordServiceTest {
    protected RecordService recordService;
    protected SubscriberService subscriberService;

    protected abstract void getService();

    @BeforeEach
    public void setUp() {
        getService();
    }

    @Test
    public void shouldThrowAtGenerateWithNoSubscribers() {
        subscriberService.set(List.of());
        assertThrows(IllegalStateException.class, () -> recordService.generate(10));
    }

    @Test
    public void shouldBeEmptyBeforeSetting() {
        List<Record> records = recordService.getAll();
        assertTrue(records.isEmpty());
    }

    @Nested
    public class WithSubscribers {

        @BeforeEach
        public void setUp() {
            List<Subscriber> subscribers = List.of(
                    new Subscriber("1234"),
                    new Subscriber("5678")
            );
            recordService.set(List.of());
            subscriberService.set(subscribers);
        }

        @Test
        public void shouldSetRecords() {
            List<Record> records = List.of(
                    new Record(
                            subscriberService.getAll().get(0),
                            subscriberService.getAll().get(1),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(1)
                            )
            );
            recordService.set(records);

            assertEquals(1, recordService.getAll().size());
        }

        @Test
        public void shouldResetSubscribers() {
            List<Record> records = getList();
            recordService.set(records);

            records = getList();
            recordService.set(records);

            assertNotNull(recordService.getAll());
            assertEquals(1, recordService.getAll().size());
        }

        @Test
        public void shouldBeSameLength() {
            for (int i = 2; i < 10; i++) {
                List<Record> records = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    records.addAll(getList());
                }
                recordService.set(records);

                assertEquals(i, recordService.getAll().size());
            }
        }

        private List<Record> getList() {
            return List.of(
                    new Record(
                            subscriberService.getAll().get(0),
                            subscriberService.getAll().get(1),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(1)
                    )
            );
        }

        @Test
        public void shouldGenerate() {
            assertDoesNotThrow(() -> recordService.generate(10));
        }

        @Test
        public void shouldMatchGenerateLength() {
            for (int i = 1; i < 20; i++) {
                assertEquals(i, recordService.generate(i).size());
            }
        }

        @Nested
        public class GeneratedRecords {

            @BeforeEach
            public void setUp() {
                recordService.set(recordService.generate(-1));
            }

            @Test
            public void shouldGetBy() {
                Subscriber subscriber = subscriberService.getAll().get(0);

                List<Record> records = recordService.getBy(subscriber.msisdn);

                assertNotNull(records);
                assertFalse(records.isEmpty());
            }

            @Test
            public void shouldGetByWithin() {
                Subscriber subscriber = subscriberService.getAll().get(0);


                List<Record> records = recordService.getByWithin(
                        subscriber.msisdn,
                        new TimeRange(getMonth())
                );

                assertNotNull(records);
                assertFalse(records.isEmpty());
            }

            @Test
            public void shouldCreateUdrReportSubscriberAll() {
                Subscriber subscriber = subscriberService.getAll().get(0);
                UsageDataReport report = recordService.createUdrReport(subscriber.msisdn, null);

                assertNotNull(report);
                assertEquals(subscriber.msisdn, report.msisdn);
            }

            @Test
            public void shouldCreateUdrReportSubscriberMonth() {
                Subscriber subscriber = subscriberService.getAll().get(0);
                UsageDataReport report = recordService.createUdrReport(subscriber.msisdn, getMonth());

                assertNotNull(report);
                assertEquals(subscriber.msisdn, report.msisdn);
            }

            @Test
            public void shouldCreateUdrReportAllAll() {
                List<UsageDataReport> report = recordService.createUdrReport(null);

                assertNotNull(report);
                assertFalse(report.isEmpty());
            }

            @Test
            public void shouldCreateUdrReportAllMonth() {
                List<UsageDataReport> report = recordService.createUdrReport(getMonth());

                assertNotNull(report);
                assertFalse(report.isEmpty());
            }

            private String getMonth() {
                LocalDate earlier = LocalDate.now().minusMonths(2);

                return String.format("%04d-%02d", earlier.getYear(), earlier.getMonthValue());
            }
        }
    }

}

