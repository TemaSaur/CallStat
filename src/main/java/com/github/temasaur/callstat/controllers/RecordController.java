package com.github.temasaur.callstat.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.temasaur.callstat.services.record.RecordService;
import com.github.temasaur.callstat.models.Record;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Контроллер с базовыми ручками о записях о звонках
 */
@RestController
@Tag(name="Records")
public class RecordController {
	private final RecordService recordService;

	@Autowired
	public RecordController(
		RecordService recordService
	) {
		this.recordService = recordService;
	}

	/**
	 * Генерирует записи о звонках
	 * @param body Параметры: максимальное количество записей
	 * @return Список записей о звонков
	 */
	@Operation(summary="Generate records", responses={
		@ApiResponse(responseCode="200", content=@Content(schema=@Schema(implementation=RecordResponse.class)), description="Records generated successfully"),
		@ApiResponse(responseCode="428", content=@Content(schema=@Schema(implementation=ErrorResponse.class)), description="Records generation failed")
	})
	@PostMapping("/records/generate")
	public ResponseEntity<Object> generate(
			@RequestBody(required=false) GenerateRecordsParams body
	) {
		try {
			int maxRecordCount = body != null && body.maxRecordCount != null ? body.maxRecordCount : -1;
			List<Record> records = recordService.generate(maxRecordCount);
			recordService.set(records);
			return ResponseEntity.ok(records);
		} catch (IllegalStateException e) {
			return ResponseEntity
				.status(428)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("error", e.getMessage()));
		}
	}

	/**
	 * Возвращает список записей о звонках
	 * @return Список записей о звонках
	 */
	@Operation(summary="Get records")
	@GetMapping("/records")
	public ResponseEntity<List<Record>> getAll() {
		return ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(recordService.getAll());
	}

	private interface RecordResponse extends List<Record> {}

	@Schema(example = "{\"error\": \"Records generation failed\"}")
	private interface ErrorResponse extends Map<String, String> {}


	public static class GenerateRecordsParams {
		@Schema(example="1000")
		@JsonProperty(required=false)
		public Integer maxRecordCount;
	}
}