package com.challenge.ForoHub.service;

import com.challenge.ForoHub.domain.User;
import com.challenge.ForoHub.domain.record.UserPayload;
import com.challenge.ForoHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserPayload createUser(User user) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var response = userRepository.save(user);
        return convertUserToUserResponse(response);
    }

    public Page<UserPayload> getAllUsers(Pageable pagination) {
        Page<User> userPage = userRepository.findAll(pagination);
        return userPage.map(this::convertUserToUserResponse);

    }

    public UserPayload getUserById(Long id) throws Exception {
        var response = userRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El usuario con id " + id + " no se encuentra registrado"));
        return convertUserToUserResponse(response);
    }

    public UserPayload updateUser(UserPayload user, Long id) throws Exception {
        var response = userRepository.findById(id).orElseThrow(() -> new ClassNotFoundException("El usuario con id " + id + " no se encuentra registrado"));
        response.setName(user.name());
        response.setEmail(user.email());

        var data = userRepository.save(response);

        return convertUserToUserResponse(data);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public UserPayload convertUserToUserResponse(User user) {
        UserPayload userPayload = new UserPayload(user.getId(), user.getName(), user.getEmail());
        return userPayload;
    }
}
