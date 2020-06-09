package com.ravi.producer.service.impl;

import com.ravi.common.message.KafkaMessage;
import com.ravi.producer.publisher.KafkaMessagePublisher;
import com.ravi.producer.service.KafkaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class KafkaServiceImpl implements KafkaService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaServiceImpl.class);

    @Autowired
    private KafkaMessagePublisher publisher;

    @Override
    public  <K extends Serializable, V extends Serializable> void publishMessage(K key, V value){
        KafkaMessage.MessageKey<K> messageKey = new KafkaMessage.MessageKey<>(key.getClass().toString(), key);
        KafkaMessage.MessageValue<V> messageValue = value == null ? null : new KafkaMessage.MessageValue<>(value.getClass().toString(), value);
        publisher.send(new KafkaMessage<>(messageKey, messageValue));
    }
}
