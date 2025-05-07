package com.example.linebot;

import com.example.linebot.model.WebhookRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @PostMapping("/reply")
    public ResponseEntity<?> reply(@RequestBody String body) {
        logger.info("Received data: {}", webhookRequest);

        // ตรวจสอบว่ามีข้อมูล groupId และ replyToken หรือไม่
        if (webhookRequest.getEvents() != null && !webhookRequest.getEvents().isEmpty()) {
            WebhookRequest.Event event = webhookRequest.getEvents().get(0);
            String replyToken = event.getReplyToken();
            String groupId = event.getSource().getGroupId();

            String message = "This is your Group ID: " + groupId;
            return sendReplyMessage(replyToken, message);
        }

        return ResponseEntity.badRequest().body("Invalid data structure");
    }

    private ResponseEntity<?> sendReplyMessage(String replyToken, String message) {
        String url = "https://api.line.me/v2/bot/message/reply";
        String jsonPayload = "{ \"replyToken\": \"" + replyToken + "\", \"messages\": [{ \"type\": \"text\", \"text\": \"" + message + "\" }] }";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_LINE_CHANNEL_ACCESS_TOKEN");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            logger.error("Error sending message to LINE", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending message to LINE");
        }
    }
}
