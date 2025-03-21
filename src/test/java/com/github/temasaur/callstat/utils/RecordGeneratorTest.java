package com.github.temasaur.callstat.utils;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.models.Record;
import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.services.record.RecordImplService;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecordGeneratorTest {
    private final int COUNT = 100;
    private List<Record> records;

    private final RecordGenerator recordGenerator;
    private final SubscriberService subscriberService;

    @Autowired
    public RecordGeneratorTest(
            RecordGenerator recordGenerator,
            SubscriberService subscriberService,
            RecordImplService recordImplService
    ) {
        this.recordGenerator = recordGenerator;
        this.subscriberService = subscriberService;
        recordImplService.set(List.of());
    }

    @Test
    public void shouldThrowWithNoSubscribers() {
        subscriberService.set(List.of());
        assertThrows(AssertionError.class, () -> recordGenerator.generate(COUNT));
    }

    @Nested
    public class WithSubscribers {

        @BeforeEach
        public void setup() {
            List<Subscriber> subscribers = List.of(
                    new Subscriber("1234"),
                    new Subscriber("5678"));
            subscriberService.set(subscribers);
            records = recordGenerator.generate(COUNT);
        }


        @Test
        public void shouldNotThrow() {
            assertDoesNotThrow(() -> recordGenerator.generate(COUNT));
        }

        @Test
        public void shouldGenerate() {
            assertNotNull(records);
            assertFalse(records.isEmpty());
            assertEquals(COUNT, records.size());
        }

        @Test
        public void shouldBeEmptyOn0() {
            List<Record> subscribers = recordGenerator.generate(0);
            assertTrue(subscribers.isEmpty());
        }

        @Test
        public void shouldMatchLength() {
            for (int i = 1; i <= 20; i++) {
                List<Record> subscribers = recordGenerator.generate(i);
                assertEquals(subscribers.size(), i);
            }
        }

        @Test
        public void shouldBeUnique() {
            Set<Record> unique = new HashSet<>(records);
            assertEquals(records.size(), unique.size());
        }

        @Test
        public void shouldAllHaveDifferentSubscribers() {
            for (Record record : records) {
                assertNotNull(record.initiator);
                assertNotNull(record.initiator);

                assertInstanceOf(Subscriber.class, record.initiator);
                assertInstanceOf(Subscriber.class, record.recipient);

                assertNotEquals(record.initiator, record.recipient);
            }
        }

        @Test
        public void shouldBeChronological() {
            LocalDateTime current = null;
            boolean first = true;
            for (Record record : records) {
                if (first) {
                    current = record.callStart;
                    first = false;
                    continue;
                }

                assertTrue(current.isBefore(record.callStart));
                assertTrue(record.callStart.isBefore(record.callEnd));

                current = record.callStart;
            }
        }
    }

    @Nested
    public class FullYear {

        @BeforeEach
        public void setup() {
            List<Subscriber> subscribers = List.of(
                    new Subscriber("1234"),
                    new Subscriber("5678"));
            subscriberService.set(subscribers);
            records = recordGenerator.generate(-1);
        }

        @Test
        public void shouldGenerate() {
            assertNotNull(records);
            assertFalse(records.isEmpty());
        }

        @Test
        public void shouldEndRecently() {
            LocalDateTime now = LocalDateTime.now();
            Record last = records.get(records.size() - 1);

            Duration duration = Duration.between(last.callStart, now);
            assertTrue(duration.toHours() < 24);
        }

    }

}
