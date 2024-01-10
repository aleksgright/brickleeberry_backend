package com.example.brickleberry_backend.Entities;

import com.example.brickleberry_backend.Enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private long id;
    private String name;
    private String surname;
    private String lastname;
    private Date date_of_birth;
    private Set<Role> roles;
}
