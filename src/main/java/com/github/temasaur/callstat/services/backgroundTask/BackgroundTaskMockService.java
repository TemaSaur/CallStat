package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.github.temasaur.callstat.models.BackgroundTask;

@Service
public class BackgroundTaskMockService extends BackgroundTaskAbstractService {
	private final ConcurrentHashMap<UUID, BackgroundTask> statuses;

	public BackgroundTaskMockService() {
		statuses = new ConcurrentHashMap<>();
	}

	@Override
    public BackgroundTask getState(UUID uuid) {
		return statuses.get(uuid);
	}

	public void setState(UUID uuid, BackgroundTask state) {
		statuses.put(uuid, state);
	}

	@Override
	public void setStatusMessage(UUID uuid, BackgroundTask.Status status, String message) {
		BackgroundTask task = statuses.get(uuid);
		task.status = status;
		task.message = message;
	}
}
