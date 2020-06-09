package com.ravi.producer.service;

import java.io.Serializable;

public interface KafkaService {

    <K extends Serializable, V extends Serializable> void publishMessage(K key, V value);
}
