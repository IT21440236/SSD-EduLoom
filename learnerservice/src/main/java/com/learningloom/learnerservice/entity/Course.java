package com.learningloom.learnerservice.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Long id;

    private String name;

    // create large string variable
    private String description;

    private Double coursePrice;;
}
