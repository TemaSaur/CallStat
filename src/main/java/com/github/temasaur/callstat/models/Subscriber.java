package com.github.temasaur.callstat.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name="subscribers", indexes = {
    @Index(name = "msisdn_idx", columnList = "msisdn")
})
public class Subscriber {
    @Id
    @GeneratedValue
    private Long id;

    // could be the primary key, more context is needed
    @Schema(example = "79123456789")
    public String msisdn;

    public Subscriber() {}

    public Subscriber(String msisdn) {
        this.msisdn = msisdn;
    }
}
