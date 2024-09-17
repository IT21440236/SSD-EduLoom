package com.coursemanagement.controller;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.ContentProgressDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.ContentProgress;
import com.coursemanagement.services.instructor.content.ContentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContentController {

    private final ContentServiceImpl contentService;

    @PostMapping("/content")
    public ResponseEntity<ContentDto> addContent(@ModelAttribute @Valid ContentDto contentDto) throws IOException {
        ContentDto courseDto1 = contentService.addContent(contentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto1);
    }

    @GetMapping("/contents")
    public ResponseEntity<List<ContentDto>> getAllContent(){
        List<ContentDto> contentDtos = contentService.getAllContents();

        return ResponseEntity.ok(contentDtos);
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<Content> getOneContent(@PathVariable("id") Long taskId){
        Content oneContent = this.contentService.getOneContent(taskId);

        return new ResponseEntity<Content>(oneContent, HttpStatus.OK);
    }

    @PatchMapping("/content/{contentId}")
    public ResponseEntity<Content> updateContentDetails(@Valid @RequestBody ContentDto contentDto, @PathVariable("contentId") Long contentId) throws IOException {
        Content updatecontentDto = this.contentService.updateContent(contentDto, contentId);

        return new ResponseEntity<Content>(updatecontentDto, HttpStatus.OK);
    }

    //new changes
    @PatchMapping("/content/status/{contentId}")
    public ResponseEntity<Content> updateContentStatus(@RequestBody String status, @PathVariable("contentId") Long contentId)  {
        Content updatecontentDto = this.contentService.updatestatusContent(status, contentId);

        return new ResponseEntity<Content>(updatecontentDto, HttpStatus.OK);
    }

    @PostMapping("/course/content/markcomplete")
    public ResponseEntity<ContentProgress> markContentAsComplete(@RequestBody ContentProgressDto learnerProgressDto) {
//        Long learnerId = Long.parseLong(leaner);
//        contentService.markContentAsComplete(leaner, contentId, courseId);
        ContentProgress learnerProgress = contentService.markContentAsComplete(learnerProgressDto);
        return new ResponseEntity<ContentProgress>(learnerProgress, HttpStatus.OK);
    }

    @GetMapping("/course/content/progress")
    public ResponseEntity<Map<Long, Double>> getLearnerProgressByCourse(@RequestParam Long learnerId) {
        Map<Long, Double> progressByCourse = contentService.getLearnerProgressByCourse(learnerId);
        return ResponseEntity.ok(progressByCourse);
    }

    @DeleteMapping("/content/{contentId}")
    public ResponseEntity<String> deleteContent(@PathVariable("contentId") Long contentId) {
        contentService.deleteContent(contentId);
        return new ResponseEntity<>("Content deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/learner/all")
    public List<ContentProgress> getAllLearnerProgress() {
        return contentService.getAllLearnerProgress();
    }
}
