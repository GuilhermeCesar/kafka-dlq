package me.guilherme.dlqkafkatest.config;

import me.guilherme.dlqkafkatest.dto.RequestTest;
import me.guilherme.dlqkafkatest.exception.RetryTestException;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.annotation.KafkaListenerAnnotationBeanPostProcessor;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.retrytopic.RetryTopicConfigurer;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.net.SocketTimeoutException;
import java.net.http.WebSocket;
import java.util.List;

//@Configuration
//@EnableKafkaRetryTopic
public class KafkaConfig {

//    @Value("${app.config.topic.teste}")
//    private String topicName;
//
//    @Bean
//    public NewTopic createTopic() {
//        return new NewTopic(topicName, 1, (short) 1);
//    }

//
//    @Bean("retryTopicConfig")
//    public RetryTopicConfiguration myRetryTopic(KafkaTemplate<String, RequestTest> template) {
//        return RetryTopicConfigurationBuilder
//                .newInstance()
//                .includeTopics(List.of(topicName))
//                .fixedBackOff(3000L)
//                .maxAttempts(3)
//                .concurrency(1)
//                .retryOn(RuntimeException.class)
//                .create(template);
//    }

//    @Bean
//    public DefaultErrorHandler errorHandler() {
//        BackOff fixedBackOff = new FixedBackOff(3000L, 3);
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
//            // logic to execute when all the retry attemps are exhausted
//        }, fixedBackOff);
//        errorHandler.addRetryableExceptions(SocketTimeoutException.class, RuntimeException.class, RetryTestException.class);
//        errorHandler.addNotRetryableExceptions(NullPointerException.class);
//        errorHandler.setSeekAfterError(false);
//
//
//        return errorHandler;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> greetingKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        // Other configurations
//        factory.setCommonErrorHandler(errorHandler());
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
//
//        return factory;
//    }

}
