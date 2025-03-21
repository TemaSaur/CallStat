package com.github.temasaur.callstat.services.subscriber;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса для управления абонентами на базе данных
 */
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
        return (List<Subscriber>) repository.findAll();
    }

    @Override
    public void set(List<Subscriber> subscribers) {
        repository.saveAll(subscribers);
    }
}
