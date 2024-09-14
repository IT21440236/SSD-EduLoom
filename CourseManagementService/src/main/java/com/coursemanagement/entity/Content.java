package com.coursemanagement.entity;

import com.coursemanagement.dto.ContentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private String contentType;

    private String title;

    @Lob
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
