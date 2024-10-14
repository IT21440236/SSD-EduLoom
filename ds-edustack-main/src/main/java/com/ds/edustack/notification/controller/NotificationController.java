package com.ds.edustack.notification.controller;

import com.ds.edustack.notification.entity.Notification;
import com.ds.edustack.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody Notification notification) {
        notificationService.sendNotification(notification.getToEmail(), notification.getCourseId(), notification.getCourseName());
        return ResponseEntity.ok("Email sent successfully");
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Optional<Notification>> getNotificationById(@PathVariable String id) {
        Optional<Notification> notification = notificationService.findNotificationById(id);
        if (notification.isPresent()) {
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

//After enrolled in a course, we can get the course details through a get request.
// From that we can obtain the relevant course details and also the toEmail