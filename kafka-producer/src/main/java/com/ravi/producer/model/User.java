package com.ravi.producer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class User implements Serializable {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
}
