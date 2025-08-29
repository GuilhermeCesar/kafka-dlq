package me.guilherme.dlqkafkatest.config;

import me.guilherme.dlqkafkatest.exception.RetryTestException;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.retrytopic.DeadLetterPublishingRecovererFactory;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationSupport;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;
import java.util.function.Consumer;

@EnableKafka
@Configuration
public class MyRetryTopicConfiguration extends RetryTopicConfigurationSupport {

    @Override
    protected void configureBlockingRetries(BlockingRetriesConfigurer blockingRetries) {
        blockingRetries
                .retryOn(RetryTestException.class)
                .backOff(new FixedBackOff(3000, 3));
    }

    @Override
    protected void manageNonBlockingFatalExceptions(List<Class<? extends Throwable>> nonBlockingFatalExceptions) {
        nonBlockingFatalExceptions.add(RetryTestException.class);
    }

    @Override
    protected void configureCustomizers(CustomizersConfigurer customizersConfigurer) {
        // Use the new 2.9 mechanism to avoid re-fetching the same records after a pause
        customizersConfigurer.customizeErrorHandler(eh -> {
            eh.setSeekAfterError(false);
        });
    }

    @Override
    protected Consumer<DeadLetterPublishingRecovererFactory> configureDeadLetterPublishingContainerFactory() {
        return dlprf -> dlprf.setPartitionResolver((cr, nextTopic) -> null);
    }

}