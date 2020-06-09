package com.ravi.producer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class KafkaProducerConfiguration{
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerConfiguration.class);
        private static final String KAFKA_PROPS_FILE = "kafka.properties";
        private Properties kafkaProperties;
        private static KafkaProducerConfiguration INSTANCE;
        private KafkaProducerConfiguration(){
            try {
                kafkaProperties = loadProperties();
            } catch (IOException e) {
                LOG.error("Error initializing KafkaConfiguration", e);
            }
        }
        public static KafkaProducerConfiguration get() {
            if(null == INSTANCE){
                INSTANCE = new KafkaProducerConfiguration();
            }
            return INSTANCE;
        }

        public String getConfig(String key) {
            //To override the config property with a VM argument
            String cfg = System.getProperty(key);
            if(cfg == null) {
                cfg = kafkaProperties.getProperty(key);
            }
            return cfg;
        }
        private Properties loadProperties() throws IOException {
            Resource fileAsResource = new ClassPathResource(KAFKA_PROPS_FILE);
            Properties props = PropertiesLoaderUtils.loadProperties(fileAsResource);
            return props;
        }
}
