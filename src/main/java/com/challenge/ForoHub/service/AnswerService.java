package com.challenge.ForoHub.service;

import com.challenge.ForoHub.domain.Answer;
import com.challenge.ForoHub.domain.record.AnswerPayload;
import com.challenge.ForoHub.domain.record.AnswerRequest;
import com.challenge.ForoHub.repository.AnswerRepository;
import com.challenge.ForoHub.repository.TopicRepository;
import com.challenge.ForoHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicService topicService;


    public AnswerPayload createAnswer(AnswerRequest answerPayload) throws Exception {

        var user = userRepository.findById(answerPayload.userId()).orElseThrow(() -> new ClassNotFoundException("El usuario no se encuentra registrado"));
        var topic = topicRepository.findById(answerPayload.topicId()).orElseThrow(() -> new ClassNotFoundException("El tópico no se encuentra registrado"));

        Answer answer = new Answer(answerPayload.message(), user, topic, answerPayload.solution());

       var response = answerRepository.save(answer);

       return convertAnswerToAnswerPayload(response);
    }

    public Page<AnswerPayload> getAnswersByTopicTitle(String title, Pageable pagination) throws Exception {
        Page<Answer> answers = answerRepository.findAllByTopicTitle(title, pagination);
        return answers.map(this::convertAnswerToAnswerPayload);
    }

    public AnswerPayload updateAnswer(AnswerRequest answerPayload, Long id) throws Exception {
        var answer = answerRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("La respuesta con id " + id + " no se encuentra registrada" ));
        var user = userRepository.findById(answerPayload.userId()).orElseThrow(() -> new ClassNotFoundException("El usuario no se encuentra registrado"));
        var topic = topicRepository.findById(answerPayload.topicId()).orElseThrow(() -> new ClassNotFoundException("El tópico no se encuentra registrado"));

        answer.setUser(user);
        answer.setTopic(topic);
        answer.setMessage(answerPayload.message());
        answer.setSolution(answerPayload.solution());

        var response = answerRepository.save(answer);

        return convertAnswerToAnswerPayload(response);
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    public AnswerPayload convertAnswerToAnswerPayload(Answer answer) {
        AnswerPayload answerPayload = new AnswerPayload(
                answer.getId(),
                answer.getMessage(),
                userService.convertUserToUserResponse(answer.getUser()),
                topicService.convertTopicToTopicResponse(answer.getTopic()),
                answer.getSolution()
        );
        return answerPayload;
    }

}
