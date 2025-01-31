/*

    we should create a seperate table for choices and correctAnswers in the
    database
 */

package com.dhanush.QuizAppFullStack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Please provide a Question statement")
    private String question;
    @NotBlank(message = "Please provide under which subject this question is gonna fall into")
    private String subject;
    @NotBlank(message = "Please provide does this question have a single answer or multiple answers")
    private String questionType;    //single answer(radio) or multiple answer(checkbox)

    @NotBlank(message = "Please provide the choices of anwers")
    @ElementCollection //used for mapping of strings, this creates a seperate
    // table for this attribute
    private List<String> choices;

    @NotBlank(message = "Please provide atleast one correct answer for the question")
    @ElementCollection
    private List<String> correctAnswers;

}
