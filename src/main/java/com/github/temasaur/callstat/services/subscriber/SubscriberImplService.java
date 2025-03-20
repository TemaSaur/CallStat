package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class SubscriberImplService extends SubscriberAbstractService {
    private final SubscriberRepository repository;

    @Autowired
    public SubscriberImplService(SubscriberRepository subscriberRepository) {
        this.repository = subscriberRepository;
    }

    @Override
    public List<Subscriber> getAll() {
        List<Subscriber> subscribers = new ArrayList<>();
        for (Subscriber subscriber : repository.findAll()) {
            subscribers.add(subscriber);
        }
        return subscribers;
    }

    @Override
    public void set(List<Subscriber> subscribers) {
        repository.saveAll(subscribers);
    }
}
