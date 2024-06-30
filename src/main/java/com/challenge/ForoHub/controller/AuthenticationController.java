package com.challenge.ForoHub.controller;

import com.challenge.ForoHub.domain.User;
import com.challenge.ForoHub.domain.record.LoginRequest;
import com.challenge.ForoHub.security.DatosJWT;
import com.challenge.ForoHub.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "Return the token necessary for all other requests")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest auth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.email(), auth.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenService.generateToken((User) authentication.getPrincipal());

        var response = token;

        return ResponseEntity.ok(new DatosJWT(response));
    }
}
