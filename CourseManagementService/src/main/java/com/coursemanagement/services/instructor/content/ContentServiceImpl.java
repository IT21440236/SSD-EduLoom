package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.Course;
import com.coursemanagement.exception.ResourceNotFoundException;
import com.coursemanagement.repository.ContentRepository;
import com.coursemanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final CourseRepository courseRepository;

    private final ContentRepository contentRepository;
    @Override
    public ContentDto addContent(ContentDto contentDto) throws IOException {
        Content content = new Content();

        content.setTitle(contentDto.getTitle());
        content.setContentType(contentDto.getContentType());
        content.setDescription(contentDto.getDescription());
        content.setStatus(contentDto.getStatus());
        content.setImg(contentDto.getImg().getBytes());

//        content.setName(contentDto.getName());
//        content.setDescription(productDto.getDescription());
//        content.setPrice(productDto.getPrice());
//        content.setImg(productDto.getImg().getBytes());

        Course course = courseRepository.findById(contentDto.getCourseId()).orElseThrow();

        content.setCourse(course);

        return contentRepository.save(content).getDto();
    }

    @Override
    public List<ContentDto> getAllContents() {
        List<Content> contents = contentRepository.findAll();

        return contents.stream().map(Content :: getDto).collect(Collectors.toList());
    }

    @Override
    public Content getOneContent(Long contentId) {
        Content oneContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        return oneContent;
    }

    @Override
    public Content updateContent(ContentDto contentDto, Long contentId) throws IOException{

        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        Course course = this.courseRepository.findById(contentDto.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course", "course id", contentDto.getCourseId()));

        updateContent.setTitle(contentDto.getTitle());
        updateContent.setDescription(contentDto.getDescription());
        updateContent.setContentType(contentDto.getContentType());
        updateContent.setStatus(contentDto.getStatus());
//        updateContent.setImg(contentDto.getImg().getBytes());
        updateContent.setCourse(course);

        return this.contentRepository.save(updateContent);

    }

    @Override
    public Content updatestatusContent(String status, Long contentId) {
        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        updateContent.setStatus(status);

        return this.contentRepository.save(updateContent);
    }
}
