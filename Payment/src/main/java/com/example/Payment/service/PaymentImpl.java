//package com.example.Payment.service;
//
//
//import com.example.Payment.repository.PaymentRepository;
//import com.example.Payment.entity.CourseInfo;
//import com.example.Payment.entity.Payments;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class PaymentImpl implements PaymentService{
//
//    @Autowired
//    PaymentClient paymentClient;
//
//    @Autowired
//    PaymentRepository paymentRepository;
//
//    @Override
//    public Map<Long, CourseInfo> getLearnerCourses(Long learnerId) {
//        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);
//
//        if (response != null && response.getBody() != null) {
//            return response.getBody(); // Corrected return statement
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void saveLearnerCourses(Long learnerId, Map<Long, CourseInfo> courses) {
//        for (Map.Entry<Long, CourseInfo> entry : courses.entrySet()) {
//            Long courseId = entry.getKey();
//            CourseInfo courseInfo = entry.getValue();
//
//            // Check if the learnerId and courseId combination already exists in the database
//            if (paymentRepository.existsByLearnerIdAndCourseName(learnerId.toString(), courseInfo.getName())) {
//                // If it exists, print a message or perform any desired action
//                System.out.println("LearnerId " + learnerId + " and CourseInfo " + courseInfo.getName() + " already exist.");
//            } else {
//                // If it doesn't exist, create and save the Payments object
//                Payments payment = new Payments();
//                payment.setLearnerId(learnerId.toString());
//                payment.setCourseName(courseInfo.getName());
//                payment.setPrice(courseInfo.getPrice());
//                payment.setDate(new Date()); // Assuming you want to set the current date
//
//                paymentRepository.save(payment);
//            }
//        }
//    }
//
//    public double calculateTotalPrice(Map<Long, CourseInfo> courses) {
//        double totalPrice = 0.0;
//        for (CourseInfo courseInfo : courses.values()) {
//            // Check if the payment status is "Not Paid"
//            if ("Not Paid".equals(courseInfo.getPaymentStatus())) {
//                totalPrice += courseInfo.getPrice();
//            }
//        }
//        return totalPrice;
//    }
//
//}




//package com.example.Payment.service;
//
//import com.example.Payment.repository.PaymentRepository;
//import com.example.Payment.entity.CourseInfo;
//import com.example.Payment.entity.Payments;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Positive;
//import javax.validation.Valid;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@Service
//public class PaymentImpl implements PaymentService {
//
//    @Autowired
//    PaymentClient paymentClient;
//
//    @Autowired
//    PaymentRepository paymentRepository;
//
//    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//
//    // Sanitize learnerId by ensuring it is a valid number (could use additional logic if needed)
//    private String sanitizeLearnerId(Long learnerId) {
//        if (learnerId != null) {
//            return learnerId.toString().replaceAll("[^0-9]", "");
//        }
//        return null;
//    }
//
//    // Sanitize course name to remove harmful characters
//    private String sanitizeCourseName(String courseName) {
//        if (courseName != null) {
//            return courseName.replaceAll("[^a-zA-Z0-9\\s]", ""); // Allows only alphanumeric and spaces
//        }
//        return null;
//    }
//
//    @Override
//    public Map<Long, CourseInfo> getLearnerCourses(@NotNull @Positive Long learnerId) {
//        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);
//
//        if (response != null && response.getBody() != null) {
//            return response.getBody();
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void saveLearnerCourses(@NotNull @Positive Long learnerId, @NotNull @Valid Map<Long, @Valid CourseInfo> courses) {
//        String sanitizedLearnerId = sanitizeLearnerId(learnerId);
//
//        for (Map.Entry<Long, CourseInfo> entry : courses.entrySet()) {
//            Long courseId = entry.getKey();
//            CourseInfo courseInfo = entry.getValue();
//
//            String sanitizedCourseName = sanitizeCourseName(courseInfo.getName());
//            // Validate the courseInfo object
//            Set<ConstraintViolation<CourseInfo>> violations = validator.validate(courseInfo);
//            if (!violations.isEmpty()) {
//                // Handle violations here (e.g., log, throw exception)
//                for (ConstraintViolation<CourseInfo> violation : violations) {
//                    System.out.println("Validation error: " + violation.getMessage());
//                    throw new IllegalArgumentException(violation.getMessage());
//                }
//            }
//
//            // Check if the learnerId and courseId combination already exists in the database
//            if (paymentRepository.existsByLearnerIdAndCourseName(sanitizedLearnerId, sanitizedCourseName)) {
//                // If it exists, print a message or perform any desired action
//                System.out.println("LearnerId " + sanitizedLearnerId + " and CourseInfo " + sanitizedCourseName + " already exist.");
//            } else {
//                // If it doesn't exist, create and save the Payments object
//                Payments payment = new Payments();
//                payment.setLearnerId(sanitizedLearnerId);
//                payment.setCourseName(sanitizedCourseName);
//                payment.setPrice(courseInfo.getPrice());
//                payment.setDate(new Date()); // Assuming you want to set the current date
//
//                paymentRepository.save(payment);
//            }
//        }
//    }
//
//    public double calculateTotalPrice(@NotNull @Valid Map<Long, @Valid CourseInfo> courses) {
//        double totalPrice = 0.0;
//        for (CourseInfo courseInfo : courses.values()) {
//            // Check if the payment status is "Not Paid" (assuming using an enum or constants is preferred)
//            if ("Not Paid".equals(courseInfo.getPaymentStatus())) {
//                totalPrice += courseInfo.getPrice();
//            }
//        }
//        return totalPrice;
//    }
//}



package com.example.Payment.service;

import com.example.Payment.repository.PaymentRepository;
import com.example.Payment.entity.CourseInfo;
import com.example.Payment.entity.Payments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.Valid;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
public class PaymentImpl implements PaymentService {

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    PaymentRepository paymentRepository;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // Sanitize learnerId by ensuring it is a valid number (could use additional logic if needed)
    private String sanitizeLearnerId(Long learnerId) {
        if (learnerId != null) {
            return learnerId.toString().replaceAll("[^0-9]", ""); // Only numeric characters
        }
        return null;
    }

    // Sanitize course name to remove harmful characters
    private String sanitizeCourseName(String courseName) {
        if (courseName != null) {
            return courseName.replaceAll("[^a-zA-Z0-9\\s]", ""); // Allows only alphanumeric characters and spaces
        }
        return null;
    }

    @Override
    public Map<Long, CourseInfo> getLearnerCourses(@NotNull @Positive Long learnerId) {
        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);

        if (response != null && response.getBody() != null) {
            return response.getBody();
        } else {
            return null;
        }
    }

    @Override
    public void saveLearnerCourses(@NotNull @Positive Long learnerId, @NotNull @Valid Map<Long, @Valid CourseInfo> courses) {
        String sanitizedLearnerId = sanitizeLearnerId(learnerId);

        for (Map.Entry<Long, CourseInfo> entry : courses.entrySet()) {
            Long courseId = entry.getKey();
            CourseInfo courseInfo = entry.getValue();

            String sanitizedCourseName = sanitizeCourseName(courseInfo.getName());
            // Validate the courseInfo object
            Set<ConstraintViolation<CourseInfo>> violations = validator.validate(courseInfo);
            if (!violations.isEmpty()) {
                // Handle violations here (e.g., log, throw exception)
                for (ConstraintViolation<CourseInfo> violation : violations) {
                    System.out.println("Validation error: " + violation.getMessage());
                    throw new IllegalArgumentException(violation.getMessage());
                }
            }

            // Check if the learnerId and courseId combination already exists in the database
            if (paymentRepository.existsByLearnerIdAndCourseName(sanitizedLearnerId, sanitizedCourseName)) {
                // If it exists, print a message or perform any desired action
                System.out.println("LearnerId " + sanitizedLearnerId + " and CourseInfo " + sanitizedCourseName + " already exist.");
            } else {
                // If it doesn't exist, create and save the Payments object
                Payments payment = new Payments();
                payment.setLearnerId(sanitizedLearnerId);
                payment.setCourseName(sanitizedCourseName);
                payment.setPrice(courseInfo.getPrice());
                payment.setDate(new Date()); // Assuming you want to set the current date

                paymentRepository.save(payment);
            }
        }
    }

    public double calculateTotalPrice(@NotNull @Valid Map<Long, @Valid CourseInfo> courses) {
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
