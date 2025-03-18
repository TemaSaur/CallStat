package com.github.temasaur.callstat.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Subscriber {
    @Id
    @GeneratedValue
    private int id;

    public String msisdn;

    public Subscriber() {}

    public Subscriber(String msisdn) {
        this.msisdn = msisdn;
    }
}
