package com.example.brickleberry_backend.Entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Resource {
    private long id;
    private String name;
    private int count;
    private int warehouseId;
}
