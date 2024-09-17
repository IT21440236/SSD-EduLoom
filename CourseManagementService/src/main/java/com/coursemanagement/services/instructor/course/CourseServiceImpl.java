package com.coursemanagement.services.instructor.course;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.CourseDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.Course;
import com.coursemanagement.exception.ResourceNotFoundException;
import com.coursemanagement.repository.ContentRepository;
import com.coursemanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ContentRepository contentRepository;

    public Course createcourse(CourseDto categoryDto){
        Course category = new Course();
        category.setName(categoryDto.getName());
        category.setCoursePrice(categoryDto.getCoursePrice());
        category.setDescription(categoryDto.getDescription());

        return courseRepository.save(category);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    @Override
    public Course updateCourse(CourseDto courseDto, Long courseId) {
        Course updateCourse = this.courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "course id", courseId));

        updateCourse.setName(courseDto.getName());
        updateCourse.setCoursePrice(courseDto.getCoursePrice());
        updateCourse.setDescription(courseDto.getDescription());

        return this.courseRepository.save(updateCourse);
    }


    @Override
    public List<ContentDto> getAllContentsByCourseId(Long courseId) {
        List<Content> contentList = contentRepository.findByCourseId(courseId);
        System.out.println(contentList);
        return contentList.stream().map(Content :: getDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long courseId) {
        this.courseRepository.delete(this.courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "Course Id", courseId)));
    }

    @Override
    public Course getCourseById(Long courseId) {
        return this.courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "Course Id", courseId));
    }

    @Override
    public Course getOneCourse(Long courseId) {
        Course oneCourse = this.courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "course Id", courseId));

        return oneCourse;
    }
}
