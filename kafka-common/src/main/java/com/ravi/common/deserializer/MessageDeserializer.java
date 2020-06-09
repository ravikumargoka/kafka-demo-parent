package com.ravi.common.deserializer;

import com.ravi.common.message.KafkaMessage;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class MessageDeserializer implements Deserializer<KafkaMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageDeserializer.class);

    @Override
    public KafkaMessage deserialize(String s, byte[] arg1) {
        KafkaMessage kafkaMessage = null;
        try(ObjectInputStream oos = new ObjectInputStream(new ByteArrayInputStream(arg1))) {
            kafkaMessage = (KafkaMessage) oos.readObject();
        } catch (Exception e) {
            LOG.error("Error during serialization." + s, e);
        }
        return kafkaMessage;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {

    }
}
