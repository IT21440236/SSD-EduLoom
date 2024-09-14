package com.coursemanagement.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Double coursePrice;

    private Set<ContentDto> contents = new HashSet<>();
}
