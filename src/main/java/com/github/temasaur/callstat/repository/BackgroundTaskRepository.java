package com.github.temasaur.callstat.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.github.temasaur.callstat.models.BackgroundTask;

public interface BackgroundTaskRepository extends CrudRepository<BackgroundTask, UUID> {
}
