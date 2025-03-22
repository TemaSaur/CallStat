package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.github.temasaur.callstat.models.BackgroundTask;

@Service
public class BackgroundTaskMockService extends BackgroundTaskAbstractService {
	private final ConcurrentHashMap<UUID, BackgroundTask.Status> statuses;

	public BackgroundTaskMockService() {
		statuses = new ConcurrentHashMap<>();
	}

	@Override
    public String getStatus(UUID uuid) {
		return statuses.get(uuid).label;
	}

	@Override
	public void setStatus(UUID uuid, BackgroundTask.Status status) {
		statuses.put(uuid, status);
	}
}
