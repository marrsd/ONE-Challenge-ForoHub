package com.challenge.ForoHub.service;


import com.challenge.ForoHub.domain.Topic;
import com.challenge.ForoHub.domain.record.TopicRequest;
import com.challenge.ForoHub.domain.record.TopicResponse;
import com.challenge.ForoHub.domain.record.UserPayload;
import com.challenge.ForoHub.repository.CourseRepository;
import com.challenge.ForoHub.repository.TopicRepository;
import com.challenge.ForoHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public TopicResponse createTopic(TopicRequest topic) throws Exception {

        var userResponse = userRepository.findById(topic.idUser()).orElseThrow(() -> new ClassNotFoundException("El usuario con id " + topic.idUser() + " no se encuentra registrado"));

        var courseResponse = courseRepository.findByName(topic.courseName()).orElseThrow(() -> new ClassNotFoundException("El nombre del curso no se encuentra registrado"));

        Topic newTopic = new Topic(topic.title(), topic.message(), true, userResponse, courseResponse);

        var response = topicRepository.save(newTopic);

        return convertTopicToTopicResponse(response);
    }

    public Page<TopicResponse> getAllTopics(Pageable pagination) {
        Page<Topic> topicsPage = topicRepository.findAll(pagination);
        return topicsPage.map(this::convertTopicToTopicResponse);

    }

    public TopicResponse getTopicById(Long id) throws Exception {
        var response = topicRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El tópico con id " + id + " no se encuentra registrado"));
        return convertTopicToTopicResponse(response);
    }

    public TopicResponse updateTopic(TopicRequest topic, Long id) throws Exception {
        var userResponse = userRepository.findById(topic.idUser()).orElseThrow(() -> new ClassNotFoundException("El usuario con id " + topic.idUser() + " no se encuentra registrado"));
        var courseResponse = courseRepository.findByName(topic.courseName()).orElseThrow(() -> new ClassNotFoundException("El nombre del curso no se encuentra registrado"));

        var response = topicRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El tópico con id " + id + " no se encuentra registrado"));

        response.setTitle(topic.title());
        response.setMessage(topic.message());
        response.setUser(userResponse);
        response.setCourse(courseResponse);

        var data = topicRepository.save(response);

        return convertTopicToTopicResponse(data);
    }

    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
    }


    public TopicResponse convertTopicToTopicResponse(Topic topic) {
        TopicResponse topicResponse = new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                (topic.getStatus()) ? "Activo" : "Cerrado",
                new UserPayload(topic.getUser().getId(), topic.getUser().getName(), topic.getUser().getEmail()),
                topic.getCourse()
        );
        return topicResponse;
    }
}

