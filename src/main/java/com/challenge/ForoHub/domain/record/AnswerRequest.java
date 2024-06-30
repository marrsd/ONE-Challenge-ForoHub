package com.challenge.ForoHub.domain.record;

import jakarta.validation.constraints.NotBlank;

public record AnswerRequest(
        Long id,

        @NotBlank
        String message,
        @NotBlank
        Long userId,
        @NotBlank
        Long topicId,
        @NotBlank
        String solution
        ) {
}
