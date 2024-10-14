package com.ds.edustack.notification.service.impl;

import com.ds.edustack.notification.UIDGenerator;
import com.ds.edustack.notification.enums.NotificationStatus;
import com.ds.edustack.notification.entity.Notification;
import com.ds.edustack.notification.feign.NotificationInterface;
import com.ds.edustack.notification.repository.NotificationRepository;
import com.ds.edustack.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationInterface notificationInterface;


    @Override
    public void sendNotification(String toEmail,
                                 String enrolledCourseId,
                                 String courseName
    ) {
        try {
            // Escape potentially harmful characters from user input
            String safeEmail = StringEscapeUtils.escapeHtml4(toEmail);
            String safeCourseName = StringEscapeUtils.escapeHtml4(courseName);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(safeEmail); // Use the escaped email
            message.setSubject(safeCourseName); // Use the escaped course name

            // Set the body of the email with escaped values
            String emailBody = "Dear " + safeEmail + ",\n\n" +
                    "Congratulations on your successful enrollment!\n\n" +
                    "Course Name: " + safeCourseName + "\n" +
                    "Enrolled Course ID: " + enrolledCourseId + "\n\n" +
                    "We're excited to have you on board. Please check your dashboard for more details about the course.\n\n" +
                    "Best Regards,\n" +
                    "Your EduLoom Team";

            message.setText(emailBody);
            mailSender.send(message);

            Notification notification = new Notification();
            notification.setId(UIDGenerator.generateEmailUID());
            notification.setToEmail(safeEmail);
            notification.setCourseId(enrolledCourseId);
            notification.setCourseName(safeCourseName);
            notification.setStatus(NotificationStatus.DELIVERED);
            notificationRepository.save(notification);

            System.out.println("Mail Sent Successfully to: " + toEmail + " for course: " + courseName + " with course id: " + enrolledCourseId);
        }
        catch (Exception e){
            Notification notification = new Notification();
            notification.setToEmail(toEmail);
            notification.setCourseId(enrolledCourseId);
            notification.setCourseName(courseName);
            notification.setStatus(NotificationStatus.valueOf(NotificationStatus.FAILED.toString()));
            notificationRepository.save(notification);

            System.out.println("Mail Sending Failed." + e.getMessage());
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Notification> findNotificationById(String id) {
        return notificationRepository.findById(id);
    }
}
