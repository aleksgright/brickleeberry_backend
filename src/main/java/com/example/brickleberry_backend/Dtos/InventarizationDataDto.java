package com.example.brickleberry_backend.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventarizationDataDto {
    private int change;
    private long typeId;
    private int terId;
    private long creatorId;
    private long rfidId;
}
