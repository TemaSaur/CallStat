package com.github.temasaur.callstat.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.temasaur.callstat.models.Subscriber;
import com.github.temasaur.callstat.services.subscriber.SubscriberService;
import com.github.temasaur.callstat.utils.SubscriberGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriberController {
    private SubscriberService subscriberService;
    public SubscriberController() {}

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    /**
     * Генерирует 20 абонентов
     * @return Список абонентов
     */
    @Operation(summary="Generate subscribers")
    @PostMapping("/subscribers/generate")
    public ResponseEntity<List<Subscriber>> generate(
        @RequestBody(required=false) GenerateSubscribersParams body
    ) {
        int count = body != null && body.subscriberCount != null ? body.subscriberCount : 10;
        List<Subscriber> subscribers = SubscriberGenerator.generate(count);
        subscriberService.set(subscribers);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(subscribers);
    }

    /**
     * Возвращает список абонентов
     * @return Список абонентов
     */
    @Operation(summary="Return all subscribers")
    @GetMapping("/subscribers")
    public List<Subscriber> getAll() {
        return subscriberService.getAll();
    }

    private static class GenerateSubscribersParams {
        @Schema(example="10")
        @JsonProperty(required=false)
        public Integer subscriberCount;
    }
}
