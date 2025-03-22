package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;

import com.github.temasaur.callstat.models.BackgroundTask;

public interface BackgroundTaskService {
    BackgroundTask getState(UUID uuid);

	void setState(UUID uuid, BackgroundTask state);
    void setStatusMessage(UUID uuid, BackgroundTask.Status status, String message);
}
