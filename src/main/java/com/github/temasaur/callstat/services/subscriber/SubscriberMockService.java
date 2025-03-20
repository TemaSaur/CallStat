package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberMockService extends SubscriberAbstractService {
    private List<Subscriber> subscribers;

    @Override
    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    @Override
    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
