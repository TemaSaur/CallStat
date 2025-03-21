package com.github.temasaur.callstat.services;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public abstract class SubscriberServiceTest {
    protected SubscriberService subscriberService;

    protected abstract void getService();

    @BeforeEach
    public void setUp() {
        getService();
    }

    @Test
    public void shouldBeEmptyBeforeSetting() {
        List<Subscriber> subscribers = subscriberService.getAll();
        assertTrue(subscribers.isEmpty());
    }

    @Test
    public void shouldSetSubscribers() {
        List<Subscriber> subscribers = List.of(
                new Subscriber("1234"),
                new Subscriber("5678")
        );
        subscriberService.set(subscribers);

        assertEquals(2, subscriberService.getAll().size());
    }

    @Test
    public void shouldResetSubscribers() {
        List<Subscriber> subscribers = List.of(
                new Subscriber("1234"),
                new Subscriber("5678")
        );
        subscriberService.set(subscribers);

        subscribers = List.of(
                new Subscriber("12345"),
                new Subscriber("67890")
        );
        subscriberService.set(subscribers);

        assertNotNull(subscriberService.getAll());
        assertEquals(2, subscriberService.getAll().size());
    }

    @Test
    public void shouldBeSameLength() {
        for (int i = 2; i < 10; i++) {
            List<Subscriber> subscribers = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                subscribers.add(new Subscriber("1234"));
            }
            subscriberService.set(subscribers);

            assertEquals(i, subscriberService.getAll().size());
        }
    }

}

