package com.ravi.consumer.client;

import com.ravi.consumer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ravi")
public class KafkaConsumerClient {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerClient.class);

    @Autowired
    private UserService userService;

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext(KafkaConsumerClient.class);
        KafkaConsumerClient client = context.getBean(KafkaConsumerClient.class);
        client.start();
    }

    private void start(){
        LOG.info("the user service: {}", userService);
        userService.getUser();
    }

}


