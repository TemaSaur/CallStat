package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class SubscriberController {
    private SubscriberService subscriberService;
    public SubscriberController() {}

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/set-subscribers")
    public void setSubscriber() {
        List<Subscriber> subscribers = Arrays.asList(
            new Subscriber("1234567890"),
            new Subscriber("1234567891")
        );
        subscriberService.setSubscribers(subscribers);
    }

    @GetMapping("/get-subscribers")
    public List<Subscriber> getSubscribers() {
        return subscriberService.getSubscribers();
    }
}
