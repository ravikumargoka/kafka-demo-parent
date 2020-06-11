package com.ravi.common.message;

import java.io.Serializable;

public class GenericKafkaMessage<K extends Serializable, V extends Serializable> implements Serializable {

    private static final long serialVersionUID = -3538697929953718028L;

    private MessageValue<V> messageValue;

    private MessageKey<K> messageKey;

    public MessageValue<V> getMessageValue() {
        return messageValue;
    }

    public MessageKey<K> getMessageKey() {
        return messageKey;
    }

    public GenericKafkaMessage(MessageKey<K> messageKey, MessageValue<V> messageValue) {
        super();
        this.messageKey = messageKey;
        this.messageValue = messageValue;
    }

    public static class MessageKey<K extends Serializable> implements Serializable {
        private static final long serialVersionUID = -4550332977038513502L;
        private K keyObj;
        private String keyClass;

        public MessageKey(String keyClass, K keyObj) {
            this.keyObj = keyObj;
            this.keyClass = keyClass;
        }

        public K getKeyObj() {
            return keyObj;
        }

        public String getKeyClass() {
            return keyClass;
        }
    }

    public static class MessageValue<V extends Serializable> implements Serializable {
        private static final long serialVersionUID = 4932066509313030425L;
        private V valueObj;
        private String valueClass;

        public MessageValue(String valueClass, V valueObj) {
            this.valueObj = valueObj;
            this.valueClass = valueClass;
        }

        public V getValueObj() {
            return valueObj;
        }

        public String getValueClass() {
            return valueClass;
        }
    }
}
