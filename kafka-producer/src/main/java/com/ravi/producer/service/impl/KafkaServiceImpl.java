package com.ravi.producer.service.impl;

import com.ravi.common.message.GenericKafkaMessage;
import com.ravi.common.message.KafkaMessage;
import com.ravi.producer.publisher.KafkaJsonMessagePublisher;
import com.ravi.producer.publisher.KafkaMessagePublisher;
import com.ravi.producer.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaMessagePublisher publisher;

    @Autowired
    private KafkaJsonMessagePublisher kafkaJsonMessagePublisher;

    @Override
    public <K extends Serializable, V extends Serializable> void publishMessage(K key, V value) {
        GenericKafkaMessage.MessageKey<K> messageKey = new GenericKafkaMessage.MessageKey<>(key.getClass().toString(), key);
        GenericKafkaMessage.MessageValue<V> messageValue;
        if (null != value) {
            messageValue = new GenericKafkaMessage.MessageValue<>(value.getClass().toString(), value);
        } else {
            messageValue = null;
        }
        publisher.send(new GenericKafkaMessage(messageKey, messageValue));
    }

    @Override
    public <K extends Serializable, V extends Serializable> void publishMessage(KafkaMessage<K, V> kafkaMessage) {
        kafkaJsonMessagePublisher.send(kafkaMessage);
    }
}
