package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса для управления абонентами на списке
 */
@Service
public class SubscriberMockService extends SubscriberAbstractService {
    private List<Subscriber> subscribers;

    @Override
    public List<Subscriber> getAll() {
        return subscribers != null ? subscribers : List.of();
    }

    @Override
    public void set(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
