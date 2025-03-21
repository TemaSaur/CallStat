package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;

import java.util.List;

/**
 * Сервис для управления абонентами
 */
public interface SubscriberService {
    List<Subscriber> getAll();
    List<Subscriber> generate (int count);
    void set(List<Subscriber> subscribers);
    boolean isEmpty();
}
