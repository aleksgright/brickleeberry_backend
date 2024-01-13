package com.example.brickleberry_backend.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAddDto {
    private String name;
    private int count;
    private int warehouse_id;
}
