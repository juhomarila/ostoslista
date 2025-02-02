package fi.ruoka.ostoslista.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.dto.NotificationDto;
import fi.ruoka.ostoslista.entity.FirebaseCloudMessagingTokenEntity;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.repository.FirebaseCloudMessagingTokenRepository;
import fi.ruoka.ostoslista.service.FirebaseCloudMessagingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final FirebaseCloudMessagingService fcmService;

    private final FirebaseCloudMessagingTokenRepository tokenRepository;

    private final OstosListaLogger logger;

    public NotificationController(FirebaseCloudMessagingService fcmService, FirebaseCloudMessagingTokenRepository tokenRepository, OstosListaLogger logger) {
        this.fcmService = fcmService;
        this.tokenRepository = tokenRepository;
        this.logger = logger;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String token, @Valid @RequestBody NotificationDto notificationDto) {
        System.out.println("notificationDto: " + notificationDto);  
        logger.postLogStart("sendNotification");
        String response = fcmService.sendNotification(token, notificationDto);
        logger.postLogEnd("sendNotification");
        return ResponseEntity.ok("Notification sent: " + response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerToken(@RequestBody Map<String, String> request) {
        logger.postLogStart("registerToken");
        String token = request.get("token");
        if (token == null || token.isEmpty()) {
            logger.postLogEnd("registerTokenFailed");
            return ResponseEntity.badRequest().body("Token is required.");
        }

        if (!tokenRepository.findByToken(token).isPresent()) {
            FirebaseCloudMessagingTokenEntity entity = new FirebaseCloudMessagingTokenEntity();
            entity.setToken(token);
            tokenRepository.save(entity);
        }
        logger.postLogEnd("registerToken");
        return ResponseEntity.ok("Token registered successfully.");
    }
}
