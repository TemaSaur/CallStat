package com.github.temasaur.callstat.utils;

import com.github.temasaur.callstat.CallStatApplication;
import com.github.temasaur.callstat.models.Subscriber;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CallStatApplication.class)
public class SubscriberGeneratorTest {
    private final int COUNT = 10;
    private List<Subscriber> subscribers;

    private final SubscriberGenerator subscriberGenerator;

    @Autowired
    public SubscriberGeneratorTest(SubscriberGenerator subscriberGenerator) {
        this.subscriberGenerator = subscriberGenerator;
    }

    @BeforeEach
    public void setup() {
        subscribers = subscriberGenerator.generate(COUNT);
    }

    @Test
    public void shouldNotThrow() {
        assertDoesNotThrow(() -> subscriberGenerator.generate(COUNT));
    }

    @Test
    public void shouldGenerate() {
        assertNotNull(subscribers);
        assertFalse(subscribers.isEmpty());
        assertEquals(COUNT, subscribers.size());
    }

    @Test
    public void shouldBeEmptyOn0() {
        List<Subscriber> subscribers = subscriberGenerator.generate(0);
        assertTrue(subscribers.isEmpty());
    }

    @Test
    public void shouldMatchLength() {
        for (int i = 1; i <= 20; i++) {
            List<Subscriber> subscribers = subscriberGenerator.generate(i);
            assertEquals(subscribers.size(), i);
        }
    }

    @Test
    public void shouldBeUnique() {
        Set<Subscriber> unique = new HashSet<>(subscribers);
        assertEquals(subscribers.size(), unique.size());
    }

    @Test
    public void shouldAllHaveValidMsisdn() {
        // https://worldpopulationreview.com/country-rankings/phone-number-length-by-country
        for (Subscriber subscriber : subscribers) {
            assertNotNull(subscriber.msisdn);
            assertTrue(subscriber.msisdn.length() >= 4);
            assertTrue(subscriber.msisdn.length() <= 17);
            assertTrue(subscriber.msisdn.chars().allMatch(Character::isDigit));
        }
    }
}
