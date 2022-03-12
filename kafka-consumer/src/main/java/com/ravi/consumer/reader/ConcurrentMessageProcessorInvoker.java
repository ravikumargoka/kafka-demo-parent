package com.ravi.consumer.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class to invoke concurrent consumer
 */
@Component
public class ConcurrentMessageProcessorInvoker {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentMessageProcessorInvoker.class);

    /**
     * Method to process all the kafka messages concurrently
     *
     * @param noOfConsumers aka no. of threads
     */
    public void consumeMessages(int noOfConsumers) {
        ExecutorService executor = Executors.newFixedThreadPool(noOfConsumers);

        final List<ConcurrentMessageProcessor> consumers = new ArrayList<>();
        for (int i = 0; i < noOfConsumers; i++) {
            ConcurrentMessageProcessor consumer = new ConcurrentMessageProcessor(i);
            consumers.add(consumer);
            executor.submit(consumer);
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (ConcurrentMessageProcessor consumer : consumers) {
                    consumer.shutdown();
                }
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ie) {
                    LOG.error("Error occurred while processing the messages", ie);
                }
            }
        });
    }
}
