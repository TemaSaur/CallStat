package com.github.temasaur.callstat.types;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "{\"error\": \"message\"}")
public interface ErrorResponse extends Map<String, String> {
}
