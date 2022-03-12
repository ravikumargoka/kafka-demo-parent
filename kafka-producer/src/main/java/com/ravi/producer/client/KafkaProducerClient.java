package com.ravi.producer.client;

import com.ravi.common.message.KafkaMessage;
import com.ravi.producer.model.User;
import com.ravi.producer.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ravi")
@Slf4j
public class KafkaProducerClient {

    @Autowired
    private KafkaService kafkaService;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(KafkaProducerClient.class);
        KafkaProducerClient client = context.getBean(KafkaProducerClient.class);
        client.start();
    }

    private void start() {
        log.info("the user service: {}", kafkaService);
        /*for(int i =0; i < 10000; i++) {
            kafkaService.publishMessage(i, "Goka family"+i);
        }*/
        kafkaService.publishMessage(getKafkaMessage());
    }

    private KafkaMessage<Integer, User> getKafkaMessage() {
        User user = new User(1243, "Goka", "Ravi", "raviblackrock@gmail.com");
        KafkaMessage<Integer, User> kafkaMessage = new KafkaMessage<>(user.getUserId(), user);
        return kafkaMessage;
    }

}


