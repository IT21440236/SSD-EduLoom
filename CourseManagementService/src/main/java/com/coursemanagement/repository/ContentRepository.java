package com.coursemanagement.repository;

import com.coursemanagement.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    List<Content> findByCourseId(Long courseId);

    @Query("SELECT COUNT(c) FROM Content c WHERE c.course.id = :courseId AND c.status = :status")
    int countByCourseId(@Param("courseId") Long courseId, @Param("status") String status);

}


