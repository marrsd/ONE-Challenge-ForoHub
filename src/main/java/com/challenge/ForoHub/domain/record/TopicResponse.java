package com.challenge.ForoHub.domain.record;

import com.challenge.ForoHub.domain.Course;


public record TopicResponse (
    Long id,
    String title,
    String message,
    String status,
    UserPayload user,
    Course course
) {
    @Override
    public Long id() {
        return id;
    }
}
