package com.example.brickleberry_backend.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarizationDataDto {
    private int expectedCount;
    private int change;
    private long type;
    private int terId;
    private int creatorId;
}
