package com.challenge.ForoHub.domain;

import com.challenge.ForoHub.repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    private Course course;

    public Topic(String title, String message, boolean status, User user, Course course) {
        this.title = title;
        this.message = message;
        this.status = status;
        this.user = user;
        this.course = course;

    }
}
