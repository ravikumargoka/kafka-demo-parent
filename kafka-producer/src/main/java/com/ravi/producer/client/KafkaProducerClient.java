package com.ravi.producer.client;

import com.ravi.producer.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ravi")
public class KafkaProducerClient {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerClient.class);
    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerClient.class);
        KafkaProducerClient client = context.getBean(KafkaProducerClient.class);
        client.start();
    }

    @Autowired
    private KafkaService kafkaService;
    private void start(){
        LOG.info("the user service: {}", kafkaService);
        kafkaService.publishMessage("12345681", "ravi K goka" );
    }

}


