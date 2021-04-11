package com.ravi.common.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class MessageSerializer implements org.apache.kafka.common.serialization.Serializer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSerializer.class);

    public byte[] serialize(String s, Object o) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream)) {
            objectOutputStream.writeObject(o);
        } catch (IOException e) {
            LOG.error("Error during serialization of input string: " +s, e);
            return new byte[0];
        }
        return arrayOutputStream.toByteArray();
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map arg0, boolean arg1) {

    }
}