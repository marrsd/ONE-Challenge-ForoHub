package com.challenge.ForoHub.domain.record;

import jakarta.validation.constraints.NotBlank;

public record TopicRequest(

        @NotBlank
        Long idUser,

        @NotBlank
        String message,

        @NotBlank
        String courseName,

        @NotBlank
        String title

) {
}
