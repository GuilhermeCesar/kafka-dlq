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

    /**
     * pt_BR = Definir os erros que darão retry
     * en = Define with error can retry a queue
     * @param nonBlockingFatalExceptions a {@link List} of fatal exceptions
     * containing the framework defaults.
     */
    @Override
    protected void manageNonBlockingFatalExceptions(List<Class<? extends Throwable>> nonBlockingFatalExceptions) {
        nonBlockingFatalExceptions.add(RetryTestException.class);
    }

    /**
     * pt_BR = Não commitar o erro
     * us = It doesn't commit in kafka with error
     * @param customizersConfigurer a {@link CustomizersConfigurer}.
     */
    @Override
    protected void configureCustomizers(CustomizersConfigurer customizersConfigurer) {
        // Use the new 2.9 mechanism to avoid re-fetching the same records after a pause
        customizersConfigurer.customizeErrorHandler(eh -> {
            eh.setSeekAfterError(false);
        });
    }

    /**
     * pt_BR = Faz o retry rebalancear
     * en = Should rebalance the retry
     * @return
     */
    @Override
    protected Consumer<DeadLetterPublishingRecovererFactory> configureDeadLetterPublishingContainerFactory() {
        return dlprf -> dlprf.setPartitionResolver((cr, nextTopic) -> null);
    }
}