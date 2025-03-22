package com.github.temasaur.callstat.services.backgroundTask;

import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.github.temasaur.callstat.models.BackgroundTask;
import com.github.temasaur.callstat.repository.BackgroundTaskRepository;

@Service
@Primary
public class BackgroundTaskImplService extends BackgroundTaskAbstractService {
	private final BackgroundTaskRepository backgroundTaskRepository;

	public BackgroundTaskImplService(BackgroundTaskRepository backgroundTaskRepository) {
		this.backgroundTaskRepository = backgroundTaskRepository;
	}

	@Override
    public BackgroundTask getState(UUID uuid) {
		return backgroundTaskRepository.findById(uuid).orElse(null);
	}

	public void setState(UUID uuid, BackgroundTask state) {
		backgroundTaskRepository.save(state);
	}

	@Override
	public void setStatusMessage(UUID uuid, BackgroundTask.Status status, String message) {
		BackgroundTask task = backgroundTaskRepository.findById(uuid).orElse(null);
		task.status = status;
		task.message = message;
		backgroundTaskRepository.save(task);
	}
}
