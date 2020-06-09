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
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            LOG.error("Error during serialization." + s, e);
            return new byte[0];
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map arg0, boolean arg1) {

    }
}