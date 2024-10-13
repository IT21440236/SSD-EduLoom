package com.coursemanagement.services.instructor.course;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.CourseDto;
import com.coursemanagement.entity.Course;
import com.coursemanagement.exception.ResourceNotFoundException;

import java.util.List;

public interface CourseService {

    Course createcourse(CourseDto categoryDto);
    List<Course> getAllCourses();

    List<ContentDto> getAllContentsByCourseId(Long courseId);

    void deleteCourse(Long courseId) throws ResourceNotFoundException;

    Course getCourseById(Long courseId) throws ResourceNotFoundException;

    Course updateCourse(CourseDto courseDto, Long courseId) throws ResourceNotFoundException;

    Course getOneCourse(Long courseId) throws ResourceNotFoundException;
}
