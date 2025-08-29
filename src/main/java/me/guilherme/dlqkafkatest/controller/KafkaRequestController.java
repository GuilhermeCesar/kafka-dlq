package me.guilherme.dlqkafkatest.controller;

import lombok.RequiredArgsConstructor;
import me.guilherme.dlqkafkatest.dto.RequestTest;
import me.guilherme.dlqkafkatest.producer.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class KafkaRequestController {

    private final KafkaProducer kafkaProducer;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void publish(@RequestBody RequestTest request) {
        kafkaProducer.sendEvents(request);
    }
}
