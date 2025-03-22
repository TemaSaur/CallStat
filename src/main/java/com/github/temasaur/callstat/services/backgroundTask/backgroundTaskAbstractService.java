package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;

import com.github.temasaur.callstat.models.BackgroundTask;

public class BackgroundTaskAbstractService implements BackgroundTaskService {
    @Override
    public String getStatus(UUID uuid) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStatus(UUID uuid, BackgroundTask.Status status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
