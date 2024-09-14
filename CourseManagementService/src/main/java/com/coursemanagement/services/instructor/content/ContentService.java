package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.entity.Content;

import java.io.IOException;
import java.util.List;

public interface ContentService {

    ContentDto addContent(ContentDto contentDto) throws IOException;

    List<ContentDto> getAllContents();

    Content getOneContent(Long taskId);

    Content updateContent(ContentDto contentDto, Long contentId) throws IOException;

    Content updatestatusContent(String status, Long contentId);
}
