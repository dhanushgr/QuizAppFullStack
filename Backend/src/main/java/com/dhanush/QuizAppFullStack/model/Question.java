/*

    we should create a seperate table for choices and correctAnswers in the
    database
 */
 package com.dhanush.QuizAppFullStack.model;
 
 import jakarta.persistence.*;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotEmpty;
 import jakarta.validation.constraints.Size;
 import lombok.Getter;
 import lombok.Setter;
 
 import java.util.List;

   // Inner enum for QuestionType
    enum QuestionType {
        SINGLE_ANSWER,  // Radio button type
        MULTIPLE_ANSWER // Checkbox type
    }
 
 @Getter
 @Setter
 @Entity
 @Table(name = "questions")
 public class Question {
 
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
 
     @NotBlank(message = "Please provide a question statement")
     @Column(nullable = false)
     private String question;
 
     @NotBlank(message = "Please provide the subject for this question")
     @Column(nullable = false)
     private String subject;
 
     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private QuestionType questionType; // Enum instead of String for safety
 
     @NotEmpty(message = "Please provide at least two choices")
     @Size(min = 2, message = "A question must have at least two choices")
     @ElementCollection
     @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
     private List<String> choices;
 
     @NotEmpty(message = "Please provide at least one correct answer")
     @ElementCollection
     @CollectionTable(name = "question_correct_answers", joinColumns = @JoinColumn(name = "question_id"))
     private List<String> correctAnswers;
 }
 