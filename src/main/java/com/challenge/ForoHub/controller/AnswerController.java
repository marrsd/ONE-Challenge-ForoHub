package com.challenge.ForoHub.controller;

import com.challenge.ForoHub.domain.Answer;
import com.challenge.ForoHub.domain.Course;
import com.challenge.ForoHub.domain.record.AnswerPayload;
import com.challenge.ForoHub.domain.record.AnswerRequest;
import com.challenge.ForoHub.service.AnswerService;
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
@RequestMapping("/answer")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new answer")
    public ResponseEntity createAnswer(@RequestBody AnswerRequest answerPayload, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var response = answerService.createAnswer(answerPayload);

        URI url = uriComponentsBuilder.path(("/answer/id")).buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved answers by topic title")
    public ResponseEntity<Page<AnswerPayload>> getAllAnswer(@RequestParam String title, @PageableDefault(size = 2, sort = {"message"}) Pageable pagination) throws Exception {
        return ResponseEntity.ok(answerService.getAnswersByTopicTitle(title, pagination));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an answer by id")
    public ResponseEntity<AnswerPayload> updateAnswer(@RequestBody AnswerRequest answerPayload, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(answerService.updateAnswer(answerPayload, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an answer by id")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
