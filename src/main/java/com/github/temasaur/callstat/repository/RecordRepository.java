package com.github.temasaur.callstat.repository;

import com.github.temasaur.callstat.models.Record;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<Record, Integer> {
}
