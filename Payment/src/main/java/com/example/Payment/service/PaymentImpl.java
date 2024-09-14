package com.example.Payment.service;


import com.example.Payment.repository.PaymentRepository;
import com.example.Payment.entity.CourseInfo;
import com.example.Payment.entity.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PaymentImpl implements PaymentService{

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public Map<Long, CourseInfo> getLearnerCourses(Long learnerId) {
        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);

        if (response != null && response.getBody() != null) {
            return response.getBody(); // Corrected return statement
        } else {
            return null;
        }
    }

    @Override
    public void saveLearnerCourses(Long learnerId, Map<Long, CourseInfo> courses) {
        for (Map.Entry<Long, CourseInfo> entry : courses.entrySet()) {
            Long courseId = entry.getKey();
            CourseInfo courseInfo = entry.getValue();

            // Check if the learnerId and courseId combination already exists in the database
            if (paymentRepository.existsByLearnerIdAndCourseName(learnerId.toString(), courseInfo.getName())) {
                // If it exists, print a message or perform any desired action
                System.out.println("LearnerId " + learnerId + " and CourseInfo " + courseInfo.getName() + " already exist.");
            } else {
                // If it doesn't exist, create and save the Payments object
                Payments payment = new Payments();
                payment.setLearnerId(learnerId.toString());
                payment.setCourseName(courseInfo.getName());
                payment.setPrice(courseInfo.getPrice());
                payment.setDate(new Date()); // Assuming you want to set the current date

                paymentRepository.save(payment);
            }
        }
    }

    public double calculateTotalPrice(Map<Long, CourseInfo> courses) {
        double totalPrice = 0.0;
        for (CourseInfo courseInfo : courses.values()) {
            // Check if the payment status is "Not Paid"
            if ("Not Paid".equals(courseInfo.getPaymentStatus())) {
                totalPrice += courseInfo.getPrice();
            }
        }
        return totalPrice;
    }

}
