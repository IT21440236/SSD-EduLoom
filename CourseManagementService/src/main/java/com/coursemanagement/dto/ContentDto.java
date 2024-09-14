package com.coursemanagement.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContentDto {

    private Long id;

    private String contentType;
    private String title;

    private String description;
    private String status;
    private byte[] byteImg;

    private Long courseId;

    private MultipartFile img;
}
