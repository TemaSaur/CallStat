package com.github.temasaur.callstat.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "CallStat", description = "CDR-UDR call reporting"))
public class DocsConfig {
}
