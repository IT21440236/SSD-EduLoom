package com.example.Payment.controller;

import com.example.Payment.entity.Completed_Payments;
import com.example.Payment.entity.CourseInfo;
import com.example.Payment.entity.Payments;
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
@CrossOrigin("*")
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
        ResponseEntity<Void> response = paymentClient.updatePaymentStatusForAllCourses(learnerId);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Payment status updated successfully for learner with ID: " + learnerId);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to update payment status for learner with ID: " + learnerId);
        }
    }

//    @GetMapping("/{learnerId}/payment-history")
//    public ResponseEntity<List<Completed_Payments>> getPaymentHistoryByLearnerId(@PathVariable String learnerId) {
//        List<Completed_Payments> paymentHistory = completedPaymentsImpl.getPaymentHistoryByLearnerId(learnerId);
//        if (paymentHistory == null || paymentHistory.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(paymentHistory);
//        }
//    }

    @GetMapping("/{learnerId}/payment-history")
    public ModelAndView getPaymentHistoryByLearnerId(@PathVariable String learnerId) {
        List<Completed_Payments> paymentHistory = completedPaymentsImpl.getPaymentHistoryByLearnerId(learnerId);
        ModelAndView modelAndView = new ModelAndView("paymentReport");
        modelAndView.addObject("paymentHistory", paymentHistory);
        return modelAndView;
    }
}
