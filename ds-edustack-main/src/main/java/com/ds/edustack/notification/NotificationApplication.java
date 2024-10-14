package com.ds.edustack.notification;

import com.ds.edustack.notification.config.TwilioConfig;
import com.ds.edustack.notification.service.NotificationService;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties
@EnableFeignClients
public class NotificationApplication {

	@Autowired
	private NotificationService senderService;

	@Autowired
	private TwilioConfig twilioConfig;

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}
	/*
	* The triggerMail() method in your NotificationApplication class is an event listener method that is triggered when the
	* application context is ready, specifically when the ApplicationReadyEvent event occurs.
	* This method is currently used to simulate sending an email upon application startup.
	*
	* In your case, since you now have an endpoint to send emails (/api/v1/notifications/send-email), you might not need this
	* method anymore. You can remove or comment out the triggerMail() method, as you can now send emails by making a POST request
	* to the /send-email endpoint.
	* This will allow you to send emails with dynamic content and to specific recipients as needed.
	*  */
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		senderService.sendSimpleEmail("kodithuwakkuyeran@gmail.com",
//				"This is email body",
//				"This is email subject");
//

//	}

//	@PostConstruct //when the application starts, this method will be called
//	public void setup() {
//		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//	}
}
