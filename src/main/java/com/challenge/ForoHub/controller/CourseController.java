package com.challenge.ForoHub.controller;


import com.challenge.ForoHub.domain.Course;
import com.challenge.ForoHub.service.CourseService;
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
@RequestMapping("/course")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new course")
    public ResponseEntity createCourse(@RequestBody Course course, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var response = courseService.createCourse(course);

        URI url = uriComponentsBuilder.path(("/course/id")).buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved courses")
    public ResponseEntity<Page<Course>> getAllCourses(@PageableDefault(size = 2, sort = {"name"}) Pageable pagination) {
        return ResponseEntity.ok(courseService.getAllCouses(pagination));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by id")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course by id")
    public ResponseEntity<Course> updatecourse(@RequestBody Course course, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(courseService.updateCourse(course, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course by id")
    public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
