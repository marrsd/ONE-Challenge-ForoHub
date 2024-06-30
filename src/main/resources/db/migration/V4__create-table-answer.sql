CREATE TABLE answer(
    id BIGINT NOT NULL AUTO_INCREMENT,
    message VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    solution VARCHAR(300) NOT NULL,

    PRIMARY KEY(id),

    CONSTRAINT fk_answer_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_answer_topic_id FOREIGN KEY(topic_id) REFERENCES topic(id)
)
