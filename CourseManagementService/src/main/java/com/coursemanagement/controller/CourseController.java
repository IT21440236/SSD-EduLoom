package com.coursemanagement.controller;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.CourseDto;
import com.coursemanagement.entity.Course;
import com.coursemanagement.services.instructor.course.CourseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    @PostMapping("/course")
    public ResponseEntity<Course> createCourse(@RequestBody @Valid CourseDto courseDto){
        Course category = courseService.createcourse(courseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ContentDto>> getOneBook(@PathVariable("courseId") Long courseId){
//        BookDto bookDto1 = this.bookService.getBookById(courseId);
        List<ContentDto> contentDtoList = this.courseService.getAllContentsByCourseId(courseId);

        return new ResponseEntity<List<ContentDto>>(contentDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping("/onecourse/{id}")
    public ResponseEntity<Course> getOneCourse(@PathVariable("id") Long courseId){
        Course oneCourse = this.courseService.getOneCourse(courseId);

        return new ResponseEntity<Course>(oneCourse, HttpStatus.OK);
    }

    @PutMapping("/course/{courseId}")
    public ResponseEntity<Course> updateCourseDetails(@RequestBody CourseDto courseDto, @PathVariable("courseId") Long courseId) {
        Course updatecourseDto = this.courseService.updateCourse(courseDto, courseId);

        return new ResponseEntity<Course>(updatecourseDto, HttpStatus.OK);
    }


}
