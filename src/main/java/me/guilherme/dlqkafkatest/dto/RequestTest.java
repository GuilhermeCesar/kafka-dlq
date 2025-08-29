package me.guilherme.dlqkafkatest.dto;

import org.apache.kafka.common.protocol.types.Field;

public record RequestTest(String nome, Boolean erro) {

    public RequestTest {
    }

    public RequestTest(String nome) {
        this(nome, Boolean.FALSE);
    }


}
