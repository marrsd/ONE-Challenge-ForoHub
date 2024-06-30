package com.challenge.ForoHub.domain.record;


import jakarta.validation.constraints.NotBlank;

public record UserPayload(
        Long id,

        @NotBlank
        String name,
        @NotBlank
        String email
) {
    public UserPayload(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
