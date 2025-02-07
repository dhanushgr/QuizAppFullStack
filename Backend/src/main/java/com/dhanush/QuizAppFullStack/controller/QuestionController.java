package com.dhanush.QuizAppFullStack.controller;

import com.dhanush.QuizAppFullStack.model.Question;
import com.dhanush.QuizAppFullStack.service.IQuestionService;
import com.dhanush.QuizAppFullStack.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuestionController {

    private final IQuestionService questionService;

    // Fetch all questions
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    // Fetch a question by ID
    @GetMapping("/questions/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
    }

    // Create a new question
    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        return ResponseEntity.status(CREATED).body(questionService.createQuestion(question));
    }

    // Update an existing question
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @Valid @RequestBody Question question) {
        try {
            return ResponseEntity.ok(questionService.updateQuestion(id, question));
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // Delete a question
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // Fetch all subjects
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects() {
        return ResponseEntity.ok(questionService.getAllSubjects());
    }

    // Fetch random questions for a user
    @GetMapping("/questions/random")
    public ResponseEntity<List<Question>> getQuestionsForUser(
            @RequestParam Integer numOfQuestions,
            @RequestParam String subjects) {
        List<Question> questions =
                questionService.getQuestionsForUser(numOfQuestions, subjects);
        return ResponseEntity.ok(questions.subList(0, Math.min(numOfQuestions, questions.size())));
    }
}