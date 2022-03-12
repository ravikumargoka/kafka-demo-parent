package com.ravi.producer.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class KafkaProducerConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerConfiguration.class);
    private static final String KAFKA_PROPS_FILE = "kafka.properties";
    private Properties kafkaProperties;
    private static KafkaProducerConfiguration INSTANCE;

    private KafkaProducerConfiguration() {
        try {
            kafkaProperties = loadProperties();
        } catch (IOException ex) {
            LOG.error("Error initializing Kafka Producer Configuration", ex);
        }
    }

    public static KafkaProducerConfiguration get() {
        if (null == INSTANCE) {
            INSTANCE = new KafkaProducerConfiguration();
        }
        return INSTANCE;
    }

    public String getConfig(String key) {
        //To override the config property with a VM argument
        String sysProperty = System.getProperty(key);
        if (StringUtils.isBlank(sysProperty)) {
            sysProperty = kafkaProperties.getProperty(key);
        }
        return sysProperty;
    }

    private Properties loadProperties() throws IOException {
        Resource fileResource = new ClassPathResource(KAFKA_PROPS_FILE);
        Properties producerProperties = PropertiesLoaderUtils.loadProperties(fileResource);
        return producerProperties;
    }
}
