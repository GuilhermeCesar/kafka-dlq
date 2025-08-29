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
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer2 {


//    @RetryableTopic(
//            backoff = @Backoff(value = 3000L, multiplier = 2, maxDelay = 7000L),
//            timeout = "1000",
//            include = {RuntimeException.class, RetryTestException.class}, traversingCauses = "true")// 3 topic N-1
    @RetryableTopic
    @KafkaListener(topics = "${app.config.topic.teste}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvents(RequestTest requestTest,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.OFFSET) long offset) throws RetryTestException {
        try {
            log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(requestTest), topic, offset);
            //validate restricted IP before process the records
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
