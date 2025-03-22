package com.github.temasaur.callstat.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="background_tasks")
public class BackgroundTask {
    @Id
    public UUID uuid;

    public Status status;

    public String message;

    public enum Status {
        RUNNING("running"),
        FINISHED("finished"),
        // CANCELED("canceled"),
        FAILED("failed");


        public final String label;
        Status(String label) {
            this.label = label;
        }
    }
}
