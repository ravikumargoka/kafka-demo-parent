package com.ravi.consumer.reader;

import com.ravi.common.message.GenericKafkaMessage;
import com.ravi.consumer.config.KafkaConsumerConfiguration;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.ravi.consumer.constants.KafkaConstants.*;
import static com.ravi.consumer.constants.KafkaConstants.KAFKA_AUTO_OFFSET_RESET_CONFIG;

/**
 * Class to consume messages concurrently
 * @param <K>
 * @param <V>
 */
public class ConcurrentMessageProcessor<K extends Serializable, V extends Serializable> implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentMessageProcessor.class);

    private Consumer<K, GenericKafkaMessage<K, V>> consumer;
    private int id;

    ConsumerRecords<K, GenericKafkaMessage<K, V>> pollRecords() {
        return consumer.poll(Duration.ofMillis(Long.MAX_VALUE));
    }

    public ConcurrentMessageProcessor(int id) {
        this.id = id;
        this.consumer = new KafkaConsumer(initConsumer());
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Arrays.asList(KafkaConsumerConfiguration.get().getConfig(KAFKA_TOPIC_NAME)));
            while (true) {
                ConsumerRecords<K, GenericKafkaMessage<K, V>> records = pollRecords();
                for (ConsumerRecord<K, GenericKafkaMessage<K, V>> record : records) {
                    GenericKafkaMessage genericKafkaMessage = record.value();
                    LOG.info("Thread id: "+id+" :: The topic name: "+record.topic()+" :: partition: "+record.partition()+" :: offset: "+record.offset());
                    LOG.info("The message details: the key: {} and the value {}", genericKafkaMessage.getMessageKey().getKeyObj(), genericKafkaMessage.getMessageValue() != null ? genericKafkaMessage.getMessageValue().getValueObj() : null);
                }
                consumer.commitAsync();
            }
        } catch (WakeupException e) {
            // ignore for shutdown
        }
        finally {
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
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
