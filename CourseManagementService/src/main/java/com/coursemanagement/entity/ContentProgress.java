package com.coursemanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contentprogress")
public class ContentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long learnerId;
    private Long contentId;
    private Long courseId;
    private boolean completed;
}
