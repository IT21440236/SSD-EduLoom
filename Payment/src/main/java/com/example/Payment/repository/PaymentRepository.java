package com.example.Payment.repository;

import com.example.Payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    boolean existsByLearnerIdAndCourseName(String learnerId, String courseName);

}
