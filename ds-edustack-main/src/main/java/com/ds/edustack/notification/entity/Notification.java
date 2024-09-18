package com.ds.edustack.notification.entity;

import com.ds.edustack.notification.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
@Entity
public class Notification {
    @Id
    private String id;
    private String toEmail;
    private String courseId;
    private String courseName;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}
