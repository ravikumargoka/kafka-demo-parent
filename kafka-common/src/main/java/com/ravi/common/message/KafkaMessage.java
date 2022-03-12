package com.ravi.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaMessage<K extends Serializable, V extends Serializable> implements Serializable {
    K key;
    V value;
}
