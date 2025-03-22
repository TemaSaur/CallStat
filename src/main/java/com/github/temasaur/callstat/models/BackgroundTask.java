package com.github.temasaur.callstat.models;

import jakarta.persistence.*;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonValue;

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

        @JsonValue
        public final String label;
        Status(String label) {
            this.label = label;
        }
    }

    public BackgroundTask() {
        uuid = UUID.randomUUID();
        status = Status.RUNNING;
        message = "";
    }

    public BackgroundTask(UUID uuid) {
        this.uuid = uuid;
        this.status = Status.RUNNING;
        this.message = "";
    }
}
