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

    @Autowired
    private KafkaService kafkaService;

    public static void main(String args[]){
        ApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerClient.class);
        KafkaProducerClient client = context.getBean(KafkaProducerClient.class);
        client.start();
    }
    private void start(){
        LOG.info("the user service: {}", kafkaService);
        for(int i =0; i < 10000; i++) {
            kafkaService.publishMessage(i, "Goka family"+i);
        }
    }

}


