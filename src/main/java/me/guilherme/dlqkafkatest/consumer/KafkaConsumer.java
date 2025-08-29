package me.guilherme.dlqkafkatest.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.guilherme.dlqkafkatest.dto.RequestTest;
import me.guilherme.dlqkafkatest.exception.RetryTestException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class KafkaConsumer {


    @RetryableTopic(dltTopicSuffix = ".dlt", retryTopicSuffix = ".retry")
    @KafkaListener(topics = "${app.config.topic.teste}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(RequestTest requestTest,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.OFFSET) long offset) throws RetryTestException {
        try {
            log.info("Recebeu: {} de {} offset {}", new ObjectMapper().writeValueAsString(requestTest), topic, offset);
            if (Boolean.TRUE.equals(requestTest.erro())) {
                throw new RetryTestException("Teste de erro");
            }

        } catch (JsonProcessingException e) {
            log.error("Erro: {}",e.getMessage(), e);
            throw new RetryTestException("Teste de erro");
        }
    }

    @DltHandler
    public void listenDLT(String user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("DLT Received : {} , from {} , offset {}",user,topic,offset);
    }
}
