//package com.coursemanagement;
//
//import com.coursemanagement.dto.ContentDto;
//import com.coursemanagement.entity.Content;
//import com.coursemanagement.entity.Course;
//import com.coursemanagement.repository.ContentRepository;
//import com.coursemanagement.repository.CourseRepository;
//import com.coursemanagement.services.instructor.content.ContentServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
////@SpringBootTest
//
//@ExtendWith(MockitoExtension.class)
//class CourseManagementServiceApplicationTests {
//
//	@Mock
//	private CourseRepository courseRepository;
//
//	@Mock
//	private ContentRepository contentRepository;
//
//	@InjectMocks
//	private ContentServiceImpl contentServiceImpl;
//
//	private ContentDto validContentDto;
//
//	@BeforeEach
//	public void setUp() {
//		validContentDto = new ContentDto();
//		validContentDto.setCourseId(1L);
//		validContentDto.setTitle("Valid Title");
//		validContentDto.setDescription("Valid Description");
//		validContentDto.setContentType("image/jpeg");
//		validContentDto.setImg(mockMultipartFile("validImage.jpg", "image/jpeg", 1024));
//	}
//
//	private MultipartFile mockMultipartFile(String fileName, String contentType, long size) {
//		MultipartFile mockFile = mock(MultipartFile.class);
//		when(mockFile.getOriginalFilename()).thenReturn(fileName);
//		when(mockFile.getContentType()).thenReturn(contentType);
//		when(mockFile.getSize()).thenReturn(size);
//		return mockFile;
//	}
//
//	@Test
//	public void testAddContent_ValidInput_ShouldReturnContentDto() throws IOException {
//		// Arrange
//		Course course = new Course();
//		course.setId(1L);
//		when(courseRepository.findById(1L)).thenReturn(java.util.Optional.of(course));
//		when(contentRepository.save(any(Content.class))).thenReturn(new Content()); // Mock save method
//
//		// Act
//		ContentDto result = contentServiceImpl.addContent(validContentDto);
//
//		// Assert
//		assertNotNull(result);
//		verify(courseRepository).findById(1L);
//		verify(contentRepository).save(any(Content.class));
//	}
//
//	@Test
//	public void testAddContent_EmptyImage_ShouldThrowException() {
//		// Arrange
//		validContentDto.setImg(null);
//
//		// Act & Assert
//		Exception exception = assertThrows(ResponseStatusException.class, () -> {
//			contentServiceImpl.addContent(validContentDto);
//		});
//		assertEquals("400 BAD_REQUEST \"Image file is required\"", exception.getMessage());
//	}
//
//	@Test
//	public void testAddContent_InvalidFileType_ShouldThrowException() {
//		// Arrange
//		validContentDto.setImg(mockMultipartFile("invalidFile.txt", "text/plain", 1024)); // Invalid type
//
//		// Act & Assert
//		Exception exception = assertThrows(ResponseStatusException.class, () -> {
//			contentServiceImpl.addContent(validContentDto);
//		});
//		assertEquals("400 BAD_REQUEST \"Only JPEG and PNG images are allowed\"", exception.getMessage());
//	}
//
//	@Test
//	public void testAddContent_ExceedsFileSizeLimit_ShouldThrowException() {
//		// Arrange
//		validContentDto.setImg(mockMultipartFile("largeImage.jpg", "image/jpeg", 6 * 1024 * 1024)); // 6MB
//
//		// Act & Assert
//		Exception exception = assertThrows(ResponseStatusException.class, () -> {
//			contentServiceImpl.addContent(validContentDto);
//		});
//		assertEquals("400 BAD_REQUEST \"Image file size exceeds the 5MB limit\"", exception.getMessage());
//	}
//
//	@Test
//	public void testAddContent_InvalidCourseId_ShouldThrowException() {
//		// Arrange
//		validContentDto.setCourseId(-1L); // Invalid Course ID
//
//		// Act & Assert
//		Exception exception = assertThrows(ResponseStatusException.class, () -> {
//			contentServiceImpl.addContent(validContentDto);
//		});
//		assertEquals("400 BAD_REQUEST \"Invalid course ID\"", exception.getMessage());
//	}
//
//	@Test
//	public void testAddContent_CourseNotFound_ShouldThrowException() {
//		// Arrange
//		validContentDto.setCourseId(99L); // Course ID that does not exist
//		when(courseRepository.findById(99L)).thenReturn(java.util.Optional.empty());
//
//		// Act & Assert
//		Exception exception = assertThrows(ResponseStatusException.class, () -> {
//			contentServiceImpl.addContent(validContentDto);
//		});
//		assertEquals("404 NOT_FOUND \"Course not found\"", exception.getMessage());
//	}
//
//}
