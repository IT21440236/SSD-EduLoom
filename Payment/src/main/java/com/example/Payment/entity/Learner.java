package com.example.Payment.entity;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Learner {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Map<Long, CourseInfo> enrolledCourses = new HashMap<>();
    private Map<Long, String> completedCourses = new HashMap<>();
    private Map<Long, String> inProgressCourses = new HashMap<>();
}