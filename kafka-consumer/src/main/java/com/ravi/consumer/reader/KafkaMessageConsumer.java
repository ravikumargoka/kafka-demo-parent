package com.ravi.consumer.reader;

import com.ravi.common.message.GenericKafkaMessage;
import com.ravi.consumer.config.KafkaConsumerConfiguration;
import org.apache.kafka.clients.consumer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.ravi.consumer.constants.KafkaConstants.*;

@Service
public class KafkaMessageConsumer<K extends Serializable, V extends Serializable> {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumer.class);

    private final Consumer<K, GenericKafkaMessage<K, V>> consumer = new KafkaConsumer<>(initConsumer());

    public void readMessages() {
        try {
            int noRecordsCount = 0;
            int giveUp = 100;
            consumer.subscribe(Arrays.asList(KafkaConsumerConfiguration.get().getConfig(KAFKA_TOPIC_NAME)));
            while (true) {
                ConsumerRecords<K, GenericKafkaMessage<K, V>> records = pollRecords();
                if (records.isEmpty()) {
                    noRecordsCount++;
                    if (noRecordsCount > giveUp) {
                        break;
                    }
                }
                for (ConsumerRecord<K, GenericKafkaMessage<K, V>> record : records) {
                    GenericKafkaMessage genericKafkaMessage = record.value();
                    LOG.info("The message details are :: partition: {}, offset: {}, headers: {}", record.partition(), record.offset(), record.headers());
                    LOG.info("The message details: the key: {} and the value {}", genericKafkaMessage.getMessageKey().getKeyObj(), genericKafkaMessage.getMessageValue() != null ? genericKafkaMessage.getMessageValue().getValueObj() : null);
                }
                consumer.commitAsync();
            }
        } catch (Exception ex) {
            LOG.error("Error occurred while consuming the messages", ex);
        } finally {
            if(null != consumer) {
                consumer.close();
            }
        }
    }

    ConsumerRecords<K, GenericKafkaMessage<K, V>> pollRecords() {
        return consumer.poll(Duration.ofMillis(10000));
    }

    private Properties initConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_BOOTSTRAP_SERVERS));
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_GROUP_ID));
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_KEY_DESERIALIZER_CLASS));
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_VALUE_DESERIALIZER_CLASS));
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_ENABLE_AUTO_COMMIT_CONFIG));
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConsumerConfiguration.get().getConfig(KAFKA_AUTO_OFFSET_RESET_CONFIG));
        return consumerProperties;
    }
}
