package com.ravi.producer.publisher;

import com.google.gson.Gson;
import com.ravi.common.message.GenericKafkaMessage;
import com.ravi.producer.config.KafkaProducerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.ravi.producer.constants.KafkaConstants.*;

@Service
@Slf4j
public class KafkaMessagePublisher<K extends Serializable, V extends Serializable> {

    private final KafkaTemplate template = new KafkaTemplate(producerFactory(), true);

    private final Gson gson = new Gson();

    public void send(GenericKafkaMessage<K, V> message) {
        try {
            String topicName = KafkaProducerConfiguration.get().getConfig(KAFKA_TOPIC_NAME);
            ProducerRecord<String, GenericKafkaMessage<K, V>> producerRecord = new ProducerRecord(topicName, message.getMessageKey().getKeyObj().toString(), message);
            template.send(producerRecord);
        } catch (Exception ex) {
            log.error("Error occurred while publishing the data to Kafka", ex);
        }

    }


    public void sendWithCallback(GenericKafkaMessage<K, V> message) {
        try {
            String topicName = KafkaProducerConfiguration.get().getConfig(KAFKA_TOPIC_NAME);
            ProducerRecord<String, GenericKafkaMessage<K, V>> producerRecord = new ProducerRecord(topicName, message.getMessageKey().getKeyObj().toString(), message);
            ListenableFuture<SendResult<String, GenericKafkaMessage<K, V>>> future = template.send(producerRecord);
            future.addCallback(new ListenableFutureCallback<SendResult<String, GenericKafkaMessage<K, V>>>() {
                @Override
                public void onSuccess(SendResult<String, GenericKafkaMessage<K, V>> result) {
                    log.info("Sent message=[" + message +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Unable to send message=["
                            + message + "] due to : ", ex);
                }
            });
        } catch (Exception ex) {
            log.error("Error occurred while publishing the data to Kafka", ex);
        }
    }

    private ProducerFactory producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_BOOTSTRAP_SERVERS));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_KEY_SERIALIZER_CLASS));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_VALUE_SERIALIZER_CLASS));
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_COMPRESSION_TYPE));
        return new DefaultKafkaProducerFactory<>(config);
    }
}
