package com.coursemanagement.repository;

import com.coursemanagement.entity.ContentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentProgressRepository extends JpaRepository<ContentProgress, Long> {
    ContentProgress findByLearnerIdAndContentId(Long learnerId, Long contentId);

    List<ContentProgress> findByLearnerId(Long learnerId);
}
