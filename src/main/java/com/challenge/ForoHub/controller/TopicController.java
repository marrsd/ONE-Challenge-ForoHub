package com.challenge.ForoHub.controller;

import com.challenge.ForoHub.domain.record.TopicRequest;
import com.challenge.ForoHub.domain.record.TopicResponse;
import com.challenge.ForoHub.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/topic")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new topic")
    public ResponseEntity createTopic(@RequestBody TopicRequest topic, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var response = topicService.createTopic(topic);

        URI url =uriComponentsBuilder.path(("/topic/id")).buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved topics")
    public ResponseEntity<Page<TopicResponse>> getAllTopics(@PageableDefault(size = 2, sort = {"title"}) Pageable pagination) {
        return ResponseEntity.ok(topicService.getAllTopics(pagination));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get topic by id")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a topic by id")
    public ResponseEntity<TopicResponse> updateTopic(@RequestBody TopicRequest topicRequest, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(topicService.updateTopic(topicRequest, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a topic by id")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
