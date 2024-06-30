package com.challenge.ForoHub.repository;

import com.challenge.ForoHub.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = "SELECT a FROM Answer a JOIN a.topic t WHERE t.title = ?1",
            countQuery = "SELECT COUNT(a) FROM Answer a JOIN a.topic t WHERE t.title = ?1")
    Page<Answer> findAllByTopicTitle(String title, Pageable pagination);
}
