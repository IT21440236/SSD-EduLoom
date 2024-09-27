//package com.example.Payment.controller;
//
//import com.example.Payment.entity.Completed_Payments;
//import com.example.Payment.entity.CourseInfo;
//import com.example.Payment.entity.Payments;
//import com.example.Payment.service.CompletedPaymentsImpl;
//import com.example.Payment.service.PaymentClient;
//import com.example.Payment.service.PaymentImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@CrossOrigin(origins = "*") // Restrict CORS to trusted domains
//@RequestMapping("api/v1/payments")
//public class PaymentController {
//
//    @Autowired
//    PaymentClient paymentClient;
//
//    @Autowired
//    PaymentImpl paymentImpl;
//
//    @Autowired
//    CompletedPaymentsImpl completedPaymentsImpl;
//
//    @GetMapping("/{learnerId}/courses")
//    public ResponseEntity<Map<Long, CourseInfo>> getLearnerCourses(@PathVariable Long learnerId) {
//        Map<Long, CourseInfo> learnerCourses = paymentImpl.getLearnerCourses(learnerId);
//        if (learnerCourses == null || learnerCourses.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            // Save the retrieved courses into the database
//            paymentImpl.saveLearnerCourses(learnerId, learnerCourses);
//            return ResponseEntity.ok(learnerCourses);
//        }
//    }
//
//    @GetMapping("/{learnerId}/total-price")
//    public ResponseEntity<Double> getTotalPriceForLearner(@PathVariable Long learnerId) {
//        // Retrieve the learner's courses from the payment client
//        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);
//
//        if (response != null && response.getBody() != null) {
//            // Calculate the total price from the courses
//            Map<Long, CourseInfo> courses = response.getBody();
//            double totalPrice = paymentImpl.calculateTotalPrice(courses);
//
//            return ResponseEntity.ok(totalPrice);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/updatePaymentStatus/{learnerId}")
//    public ResponseEntity<String> updatePaymentStatusForAllCourses(@PathVariable Long learnerId) {
//        ResponseEntity<Void> response = paymentClient.updatePaymentStatusForAllCourses(learnerId);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return ResponseEntity.ok("Payment status updated successfully for learner with ID: " + learnerId);
//        } else {
//            return ResponseEntity.status(response.getStatusCode()).body("Failed to update payment status for learner with ID: " + learnerId);
//        }
//    }
//
//    @GetMapping("/{learnerId}/payment-history")
//    public ModelAndView getPaymentHistoryByLearnerId(@PathVariable String learnerId) {
//        List<Completed_Payments> paymentHistory = completedPaymentsImpl.getPaymentHistoryByLearnerId(learnerId);
//        ModelAndView modelAndView = new ModelAndView("paymentReport");
//
//        // Ensure that paymentHistory is properly handled to prevent XSS
//        modelAndView.addObject("paymentHistory", paymentHistory);
//        return modelAndView;
//    }
//}



package com.example.Payment.controller;

import com.example.Payment.entity.Completed_Payments;
import com.example.Payment.entity.CourseInfo;
import com.example.Payment.service.CompletedPaymentsImpl;
import com.example.Payment.service.PaymentClient;
import com.example.Payment.service.PaymentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Consider restricting CORS to trusted domains in production
@RequestMapping("api/v1/payments")
public class PaymentController {

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    PaymentImpl paymentImpl;

    @Autowired
    CompletedPaymentsImpl completedPaymentsImpl;

    @GetMapping("/{learnerId}/courses")
    public ResponseEntity<Map<Long, CourseInfo>> getLearnerCourses(@PathVariable Long learnerId) {
        // Validate learnerId to ensure it's a valid number (if needed)
        if (learnerId == null || learnerId <= 0) {
            return ResponseEntity.badRequest().body(null); // Invalid learnerId
        }

        Map<Long, CourseInfo> learnerCourses = paymentImpl.getLearnerCourses(learnerId);
        if (learnerCourses == null || learnerCourses.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            // Save the retrieved courses into the database
            paymentImpl.saveLearnerCourses(learnerId, learnerCourses);
            return ResponseEntity.ok(learnerCourses);
        }
    }

    @GetMapping("/{learnerId}/total-price")
    public ResponseEntity<Double> getTotalPriceForLearner(@PathVariable Long learnerId) {
        // Validate learnerId
        if (learnerId == null || learnerId <= 0) {
            return ResponseEntity.badRequest().build(); // Invalid learnerId
        }

        // Retrieve the learner's courses from the payment client
        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);

        if (response != null && response.getBody() != null) {
            // Calculate the total price from the courses
            Map<Long, CourseInfo> courses = response.getBody();
            double totalPrice = paymentImpl.calculateTotalPrice(courses);

            return ResponseEntity.ok(totalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updatePaymentStatus/{learnerId}")
    public ResponseEntity<String> updatePaymentStatusForAllCourses(@PathVariable Long learnerId) {
        // Validate learnerId
        if (learnerId == null || learnerId <= 0) {
            return ResponseEntity.badRequest().body("Invalid learner ID"); // Invalid learnerId
        }

        ResponseEntity<Void> response = paymentClient.updatePaymentStatusForAllCourses(learnerId);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Payment status updated successfully for learner with ID: " + learnerId);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to update payment status for learner with ID: " + learnerId);
        }
    }

    @GetMapping("/{learnerId}/payment-history")
    public ModelAndView getPaymentHistoryByLearnerId(@PathVariable String learnerId) {
        // Validate learnerId
        if (learnerId == null || learnerId.isEmpty()) {
            return new ModelAndView("error").addObject("message", "Invalid learner ID"); // Invalid learnerId
        }

        List<Completed_Payments> paymentHistory = completedPaymentsImpl.getPaymentHistoryByLearnerId(learnerId);
        ModelAndView modelAndView = new ModelAndView("paymentReport");

        // Ensure that paymentHistory is properly handled to prevent XSS
        modelAndView.addObject("paymentHistory", paymentHistory);
        return modelAndView;
    }
}
