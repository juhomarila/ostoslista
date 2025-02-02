package fi.ruoka.ostoslista.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import fi.ruoka.ostoslista.dto.NotificationDto;

@Service
public class FirebaseCloudMessagingService {

    public String sendNotification(String token, NotificationDto notificationDto) {
        Notification notification = Notification.builder()
                .setTitle(notificationDto.getTitle())
                .setBody(notificationDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Sent message: " + response);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send FCM message", e);
        }
    }
}

