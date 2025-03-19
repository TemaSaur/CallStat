package com.github.temasaur.callstat.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Subscriber {
    @Id
    @GeneratedValue
    private int id;

    @Schema(example = "79123456789")
    public String msisdn;

    public Subscriber() {}

    public Subscriber(String msisdn) {
        this.msisdn = msisdn;
    }
}
