package com.challenge.ForoHub.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User user;
    private String solution;

    public Answer(String message, User user, Topic topic, String solution) {
        this.message = message;
        this.user = user;
        this.topic = topic;
        this.solution = solution;
    }
}
