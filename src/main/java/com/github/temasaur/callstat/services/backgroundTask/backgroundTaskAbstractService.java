package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;

import com.github.temasaur.callstat.models.BackgroundTask;
import com.github.temasaur.callstat.models.BackgroundTask.Status;

public class BackgroundTaskAbstractService implements BackgroundTaskService {
    @Override
    public BackgroundTask getState(UUID uuid) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setState(UUID uuid, BackgroundTask state) {
        throw new UnsupportedOperationException("Unimplemented method 'setState'");
    }

    @Override
    public void setStatusMessage(UUID uuid, Status status, String message) {
        throw new UnsupportedOperationException("Unimplemented method 'setStatusMessage'");
    }
}
