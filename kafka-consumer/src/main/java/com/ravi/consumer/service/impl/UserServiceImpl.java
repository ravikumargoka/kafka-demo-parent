package com.ravi.consumer.service.impl;

import com.ravi.consumer.reader.ConcurrentMessageProcessorInvoker;
import com.ravi.consumer.reader.KafkaMessageConsumer;
import com.ravi.consumer.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private KafkaMessageConsumer consumer;

    @Autowired
    private ConcurrentMessageProcessorInvoker concurrentMessageProcessorInvoker;

    @Override
    public void getUser(){
        consumer.readMessages();
    }

    @Override
    public void consumeConcurrent(){
        concurrentMessageProcessorInvoker.consumeMessages(10);
    }
}
