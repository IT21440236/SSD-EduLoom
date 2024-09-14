package com.learningloom.learnerservice.repository;

import com.learningloom.learnerservice.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
