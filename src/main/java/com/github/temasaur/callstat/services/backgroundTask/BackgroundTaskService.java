package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;

import com.github.temasaur.callstat.models.BackgroundTask;

public interface BackgroundTaskService {
    void setStatus(UUID uuid, BackgroundTask.Status status);
    String getStatus(UUID uuid);
}
