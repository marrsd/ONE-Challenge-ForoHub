CREATE TABLE topic(
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    message VARCHAR(300) NOT NULL,
    status TINYINT NOT NULL,
    users_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,

    PRIMARY KEY(id),

    CONSTRAINT fk_topic_user_id FOREIGN KEY(users_id) REFERENCES users(id),
    CONSTRAINT fk_topic_course_id FOREIGN KEY(course_id) REFERENCES course(id)
)
