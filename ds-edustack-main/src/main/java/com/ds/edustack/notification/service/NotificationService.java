package com.ds.edustack.notification.service;

import com.ds.edustack.notification.entity.Notification;

import java.util.Optional;

public interface NotificationService {
    void sendNotification(String toEmail, String enrolledCourseId, String courseName);

    Optional<Notification> findNotificationById(String id);
}
