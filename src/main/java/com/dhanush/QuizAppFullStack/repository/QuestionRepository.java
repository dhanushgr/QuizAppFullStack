package com.dhanush.QuizAppFullStack.repository;

import com.dhanush.QuizAppFullStack.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.ContentHandler;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubject(); //customized method

    Page<Question> findBySubject(String subject, Pageable pageable);
}
