package com.github.temasaur.callstat.controllers;

import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.SubscriberGenerator;
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
    public String setSubscriber() {
        subscriberService.setSubscribers(SubscriberGenerator.generate());
        return "OK";
    }

    @GetMapping("/get-subscribers")
    public List<Subscriber> getSubscribers() {
        return subscriberService.getSubscribers();
    }
}
