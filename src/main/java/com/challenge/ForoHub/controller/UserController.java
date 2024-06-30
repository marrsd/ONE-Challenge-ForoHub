package com.challenge.ForoHub.controller;

import com.challenge.ForoHub.domain.User;
import com.challenge.ForoHub.domain.record.UserPayload;
import com.challenge.ForoHub.service.UserService;
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
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    @Operation(summary = "Create a new user")
    public ResponseEntity createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        var response = userService.createUser(user);

        URI url =uriComponentsBuilder.path(("/user/id")).buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved users")
    public ResponseEntity<Page<UserPayload>> getAllUsers(@PageableDefault(size = 2, sort = {"name"}) Pageable pagination) {
        return ResponseEntity.ok(userService.getAllUsers(pagination));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserPayload> getUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user by id")
    public ResponseEntity<UserPayload> updateUser(@RequestBody UserPayload userPayload, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.updateUser(userPayload, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
