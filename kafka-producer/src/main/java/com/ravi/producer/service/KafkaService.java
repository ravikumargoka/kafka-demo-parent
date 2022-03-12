package com.ravi.producer.service;

import com.ravi.common.message.KafkaMessage;

import java.io.Serializable;

public interface KafkaService {

    <K extends Serializable, V extends Serializable> void publishMessage(K key, V value);

    <K extends Serializable, V extends Serializable> void publishMessage(KafkaMessage<K, V> kafkaMessage);
}
