package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;

import java.util.List;

public interface SubscriberService {
    List<Subscriber> getAll();
    void set(List<Subscriber> subscribers);
}
