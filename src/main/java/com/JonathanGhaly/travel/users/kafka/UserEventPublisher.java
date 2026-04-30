package com.JonathanGhaly.travel.users.kafka;

import com.JonathanGhaly.travel.users.dto.UserEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void publish(UserEventDto event) {
        log.info("Publishing user event for user: {}", event.userId());
        kafkaTemplate.send(TOPIC, event.userId().toString(), event);
    }
}