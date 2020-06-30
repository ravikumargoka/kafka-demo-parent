package com.ravi.producer.service.impl;

import com.ravi.common.message.GenericKafkaMessage;
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
        GenericKafkaMessage.MessageKey<K> messageKey = new GenericKafkaMessage.MessageKey<>(key.getClass().toString(), key);
        GenericKafkaMessage.MessageValue<V> messageValue;
        if(null != value){
            messageValue = new GenericKafkaMessage.MessageValue<>(value.getClass().toString(), value);
        }
        else{
            messageValue = null;
        }
        publisher.send(new GenericKafkaMessage<>(messageKey, messageValue));
    }
}
