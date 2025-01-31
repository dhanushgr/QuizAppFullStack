package com.dhanush.QuizAppFullStack.repository;

import com.dhanush.QuizAppFullStack.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
