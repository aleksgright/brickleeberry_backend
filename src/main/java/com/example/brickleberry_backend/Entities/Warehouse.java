package com.example.brickleberry_backend.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {
    private int id;
    private String name;
    private int territoryId;
    private long responsiblePersonId;
}
