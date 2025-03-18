package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;

import java.util.List;
public abstract class SubscriberAbstractService implements SubscriberService {
    public SubscriberAbstractService() {}

    @Override
    public List<Subscriber> getSubscribers() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setSubscribers(List<Subscriber> subscribers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
