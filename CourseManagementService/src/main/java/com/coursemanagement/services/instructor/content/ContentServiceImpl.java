package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.ContentProgressDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.ContentProgress;
import com.coursemanagement.entity.Course;
import com.coursemanagement.exception.ResourceNotFoundException;
import com.coursemanagement.repository.ContentProgressRepository;
import com.coursemanagement.repository.ContentRepository;
import com.coursemanagement.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.owasp.esapi.Validator;
import org.owasp.esapi.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final CourseRepository courseRepository;

    private final ContentRepository contentRepository;

    private final ContentProgressRepository learnerProgressRepository;
    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
    @Override
    public ContentDto addContent(ContentDto contentDto) throws IllegalArgumentException,IOException {

        // Validate course ID
        if (contentDto.getCourseId() == null || contentDto.getCourseId() <= 0) {
            logger.error("Invalid course ID: {}", contentDto.getCourseId());
            throw new IllegalArgumentException("Invalid course ID");
        }

        // Validate image file
        // Checks if the image file is provided (not null or empty). Prevents cases where
        // an empty or null file might be passed which could cause errors later in processing.
        if (contentDto.getImg() == null || contentDto.getImg().isEmpty()) {
            logger.error("Image file is required");
            throw new IllegalArgumentException("Image file is required");
        }

        // Check for file size (e.g., limit to 5MB)
        // Prevents users from uploading excessively large files, which can cause DoS (Denial of Service)
        // by consuming server resources. This is especially important when handling file uploads.
        if (contentDto.getImg().getSize() > 5 * 1024 * 1024) { // 5MB limit
            logger.error("Image file size exceeds limit");
            throw new IllegalArgumentException("Image file size exceeds the 5MB limit");
        }

        // Check for file type (only allow JPEG or PNG)
        // This ensures that only image files with safe MIME types (JPEG/PNG) are accepted.
        // Helps prevent the upload of malicious files (e.g., executable files or scripts disguised as images).
        String contentType = contentDto.getImg().getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            logger.error("Invalid image file type: {}", contentType);
            throw new IllegalArgumentException("Only JPEG and PNG images are allowed");
        }

        // Create and populate Content entity
        // Once all validation is passed, create a new Content entity to be saved.
        // The validated data (title, content type, description, status, image, and course) are set in the entity.
        Content content = new Content();
        content.setTitle(contentDto.getTitle());
        content.setContentType(contentDto.getContentType());
        content.setDescription(contentDto.getDescription());
        content.setStatus(contentDto.getStatus());
        content.setImg(contentDto.getImg().getBytes()); // Convert MultipartFile to byte[]

        // Find and set Course entity
        Course course = courseRepository.findById(contentDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        content.setCourse(course);

        // Save content to repository and return DTO
        return contentRepository.save(content).getDto();
    }

    @Override
    public List<ContentDto> getAllContents() {
        List<Content> contents = contentRepository.findAll();

        return contents.stream().map(Content :: getDto).collect(Collectors.toList());
    }

    @Override
    public Content getOneContent(Long contentId) throws ResourceNotFoundException {
        Content oneContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content Not Found"));

        return oneContent;
    }

    @Override
    public Content updateContent(ContentDto contentDto, Long contentId) throws IllegalArgumentException, ResourceNotFoundException {
        if (contentDto.getCourseId() == null||contentDto.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course ID");
        }
        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content Not Found"));

        Course course = this.courseRepository.findById(contentDto.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        updateContent.setTitle(contentDto.getTitle());
        updateContent.setDescription(contentDto.getDescription());
        updateContent.setContentType(contentDto.getContentType());
        updateContent.setStatus(contentDto.getStatus());
//        updateContent.setImg(contentDto.getImg().getBytes());
        updateContent.setCourse(course);

        return this.contentRepository.save(updateContent);

    }

    @Override
    public Content updatestatusContent(String status, Long contentId) throws ResourceNotFoundException {
        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content Not Found"));

        updateContent.setStatus(status);

        return this.contentRepository.save(updateContent);
    }

    @Override
    public ContentProgress markContentAsComplete(ContentProgressDto learnerProgressDto) throws IllegalArgumentException{
        Long learnerId = learnerProgressDto.getLearnerId();
        Long contentId = learnerProgressDto.getContentId();
        ContentProgress learnerProgress = learnerProgressRepository.findByLearnerIdAndContentId(learnerId, contentId);
        if (learnerProgress == null) {
            learnerProgress = new ContentProgress();
            learnerProgress.setLearnerId(learnerId);
            learnerProgress.setContentId(contentId);
            learnerProgress.setCourseId(learnerProgressDto.getCourseId());
            learnerProgress.setCompleted(true);
            return learnerProgressRepository.save(learnerProgress);
        } else {
            learnerProgress.setCompleted(true);
            return learnerProgressRepository.save(learnerProgress);
        }
    }

    @Override
    public Map<Long, Double> getLearnerProgressByCourse(Long learnerId) throws IllegalArgumentException{
        if (learnerId == null || learnerId <= 0) {
            throw new IllegalArgumentException("Invalid learner ID");
        }

        List<ContentProgress> learnerProgressList = learnerProgressRepository.findByLearnerId(learnerId);

        // Grouping learner progress by courseId
        Map<Long, List<ContentProgress>> progressByCourse = learnerProgressList.stream()
                .collect(Collectors.groupingBy(ContentProgress::getCourseId));

        // Calculating completion percentage for each course
        Map<Long, Double> progressByCourseMap = new HashMap<>();
        for (Map.Entry<Long, List<ContentProgress>> entry : progressByCourse.entrySet()) {
            Long courseId = entry.getKey();
            List<ContentProgress> courseProgress = entry.getValue();

            // Counting completed contents
            long completedContents = courseProgress.stream()
                    .filter(ContentProgress::isCompleted)
                    .count();

            String status = "Accepted";
            // Total contents in the course
//            long totalContents = courseProgress.size();
            int contentCount = contentRepository.countByCourseId(courseId, status);

            // Calculating completion percentage
            double completionPercentage = ((double) completedContents / contentCount) * 100;

            // Putting the completion percentage in the map
            progressByCourseMap.put(courseId, completionPercentage);
        }

        return progressByCourseMap;
    }

    @Override
    public void deleteContent(Long contentId) throws ResourceNotFoundException {
        this.contentRepository.delete(this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content Not Found")));
    }

    @Override
    public List<ContentProgress> getAllLearnerProgress() {
        return learnerProgressRepository.findAll();
    }

    // Helper method to validate input using ESAPI
    private String validateInput(Validator validator, String fieldName, String input, int maxLength, boolean allowNull) throws IllegalArgumentException {
        try {
            return validator.getValidInput(fieldName, input, "SafeString", maxLength, allowNull);
        } catch (ValidationException e) {
            throw new IllegalArgumentException(fieldName + " is invalid: " + e.getMessage());
        }
    }
}
