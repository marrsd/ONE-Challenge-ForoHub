package com.challenge.ForoHub.domain.record;

import jakarta.validation.constraints.NotBlank;

public record AnswerPayload(
        Long id,

        String message,
        UserPayload userWhoAnswer,
        TopicResponse topicResponse,
        String solution

) {
}
