package com.coursemanagement.entity;

import com.coursemanagement.dto.ContentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Content type is required")
    private String contentType;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @Lob
    @NotBlank(message = "Description is required")
    private String description;

    private String status;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Course course;

    public ContentDto getDto(){
        ContentDto contentDto = new ContentDto();

        contentDto.setId(id);
        contentDto.setContentType(contentType);
        contentDto.setTitle(title);
        contentDto.setDescription(description);
        contentDto.setByteImg(img);
        contentDto.setCourseId(course.getId());

//        productDto.setId(id);
//        productDto.setName(name);
//        productDto.setPrice(price);
//        productDto.setDescription(description);
//        productDto.setByteImg(img);
//        productDto.setCategoryId(category.getId());

        return contentDto;

    }
}
