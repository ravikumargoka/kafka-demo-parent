package com.ravi.common.deserializer;

import com.ravi.common.message.GenericKafkaMessage;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class MessageDeserializer implements Deserializer<GenericKafkaMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageDeserializer.class);

    @Override
    public GenericKafkaMessage deserialize(String s, byte[] arg1) {
        GenericKafkaMessage genericKafkaMessage = null;
        try (ObjectInputStream oos = new ObjectInputStream(new ByteArrayInputStream(arg1))) {
            genericKafkaMessage = (GenericKafkaMessage) oos.readObject();
        } catch (Exception e) {
            LOG.error("Error during de-serialization of input string: " + s, e);
        }
        return genericKafkaMessage;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {

    }
}
