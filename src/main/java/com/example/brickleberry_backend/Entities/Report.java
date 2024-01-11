package com.example.brickleberry_backend.Entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Report {
    private long id;
    private long personId;
    private long invResultId;
    private Date date;
    private String description;
}
