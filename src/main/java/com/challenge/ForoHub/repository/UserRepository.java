package com.challenge.ForoHub.repository;

import com.challenge.ForoHub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
