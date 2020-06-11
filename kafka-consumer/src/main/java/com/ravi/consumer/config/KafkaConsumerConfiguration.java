package com.ravi.consumer.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class KafkaConsumerConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerConfiguration.class);
    private static final String KAFKA_PROPS_FILE = "kafka.properties";
    private Properties kafkaProperties;
    private static KafkaConsumerConfiguration INSTANCE;
    private KafkaConsumerConfiguration(){
        try {
            kafkaProperties = loadProperties();
        } catch (IOException ex) {
            LOG.error("Error initializing Kafka Consumer Configuration", ex);
        }
    }
    public static KafkaConsumerConfiguration get() {
        if(null == INSTANCE){
            INSTANCE = new KafkaConsumerConfiguration();
        }
        return INSTANCE;
    }

    public String getConfig(String key) {
        //To override the config property with a VM argument
        String sysProperty = System.getProperty(key);
        if(StringUtils.isBlank(sysProperty)) {
            sysProperty = kafkaProperties.getProperty(key);
        }
        return sysProperty;
    }
    private Properties loadProperties() throws IOException {
        Resource fileResource = new ClassPathResource(KAFKA_PROPS_FILE);
        Properties consumerProperties = PropertiesLoaderUtils.loadProperties(fileResource);
        return consumerProperties;
    }
}
