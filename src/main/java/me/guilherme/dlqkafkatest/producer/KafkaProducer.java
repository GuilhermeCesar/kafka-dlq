package me.guilherme.dlqkafkatest.producer;

import me.guilherme.dlqkafkatest.dto.RequestTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, RequestTest> kafkaTemplate;


    @Value("${app.config.topic.teste}")
    private String topicName;



    public void sendEvents(RequestTest user) {
        try {
            kafkaTemplate.send(topicName, UUID.randomUUID().toString(),user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
