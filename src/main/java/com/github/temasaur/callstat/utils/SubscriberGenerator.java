package com.github.temasaur.callstat.utils;

import com.github.temasaur.callstat.models.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Генератор записей об абонентах
 */
public class SubscriberGenerator {
    public static List<Subscriber> generate(int count) {
        List<Subscriber> subscribers = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            // generate random 10 digits after 79
            StringBuilder msisdn = new StringBuilder("79");
            for (int j = 0; j < 9; j++) {
                msisdn.append((int) (Math.random() * 10));
            }

            subscribers.add(new Subscriber(msisdn.toString()));
        }

        return subscribers;
    }
}
