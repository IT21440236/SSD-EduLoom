package com.coursemanagement.services.instructor.course;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.CourseDto;
import com.coursemanagement.entity.Course;

import java.util.List;

public interface CourseService {

    Course createcourse(CourseDto categoryDto);
    List<Course> getAllCourses();

    List<ContentDto> getAllContentsByCourseId(Long courseId);

    void deleteCourse(Long courseId);

    Course getCourseById(Long courseId);

    Course updateCourse(CourseDto courseDto, Long courseId);

    Course getOneCourse(Long courseId);
}
