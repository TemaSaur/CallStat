package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.utils.SubscriberGenerator;

import java.util.List;

/**
 * Реализация сервиса для управления абонентами по умолчанию
 */
public abstract class SubscriberAbstractService implements SubscriberService {
    public SubscriberAbstractService() {}

    @Override
    public List<Subscriber> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void set(List<Subscriber> subscribers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Subscriber> generate (int count) {
        return SubscriberGenerator.generate(count);
    }
}
