package com.ravi.producer.publisher;

import com.ravi.common.message.KafkaMessage;
import com.ravi.producer.config.KafkaProducerConfiguration;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Properties;

import static com.ravi.producer.constants.KafkaConstants.*;

@Service
public class KafkaMessagePublisher<K extends Serializable, V extends Serializable> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessagePublisher.class);

    private final Producer producer = new org.apache.kafka.clients.producer.KafkaProducer(loadProperties());


    public void send(KafkaMessage<K, V> message) {
        try {
            String topicName = KafkaProducerConfiguration.get().getConfig(KAFKA_TOPIC_NAME);
            LOG.info("The topic name: {}", topicName);
            ProducerRecord<String, KafkaMessage<K, V>> producerRecord = new ProducerRecord(
                    KafkaProducerConfiguration.get().getConfig(KAFKA_TOPIC_NAME), message.getMessageKey().getKeyObj().toString(), message);
            LOG.info("Kafka message key : " + message.getMessageKey().getKeyObj());
            LOG.info("Kafka message value : " + message.getMessageValue().getValueObj());
            producer.send(producerRecord);
        }
        catch(Exception ex){
            LOG.error("Error occurred while publishing the data to Kafka", ex);
        }

    }

    private Properties loadProperties(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_BOOTSTRAP_SERVERS));
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_KEY_SERIALIZER_CLASS));
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProducerConfiguration.get().getConfig(KAFKA_VALUE_SERIALIZER_CLASS));
        return kafkaProperties;
    }

}
