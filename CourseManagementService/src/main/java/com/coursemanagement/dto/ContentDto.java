package com.coursemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContentDto {

    private Long id;

    @NotBlank(message = "Content type is required")
    private String contentType;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;
    private String status;
    private byte[] byteImg;

    private Long courseId;

    private MultipartFile img;
}
