package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.ContentProgressDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.ContentProgress;
import com.coursemanagement.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContentService {

    ContentDto addContent(ContentDto contentDto) throws IOException;

    List<ContentDto> getAllContents();

    Content getOneContent(Long taskId) throws ResourceNotFoundException;

    Content updateContent(ContentDto contentDto, Long contentId) throws IOException, ResourceNotFoundException;

    Content updatestatusContent(String status, Long contentId) throws ResourceNotFoundException;

    public ContentProgress markContentAsComplete(ContentProgressDto learnerProgressDto);

    public Map<Long, Double> getLearnerProgressByCourse(Long learnerId);

    void deleteContent(Long contentId) throws ResourceNotFoundException;

    List<ContentProgress> getAllLearnerProgress();
}
