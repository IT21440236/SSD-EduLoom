package com.ds.edustack.notification.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "notification-service") //name of the service you want to call
public interface NotificationInterface {
    //Mention the method signatures you want to call from the service
}
