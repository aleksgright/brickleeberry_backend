package com.example.brickleberry_backend.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Animal {
    private long id;
    private String typeName;
    private boolean endangered;
    private long count;
}
