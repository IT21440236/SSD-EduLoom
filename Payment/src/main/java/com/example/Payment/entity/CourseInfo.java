package com.example.Payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    private String name;
    private Double price;
    private String paymentStatus = "Not Paid";
}
