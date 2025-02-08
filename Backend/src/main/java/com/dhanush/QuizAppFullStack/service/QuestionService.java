package com.dhanush.QuizAppFullStack.service;

import com.dhanush.QuizAppFullStack.model.Question;
import com.dhanush.QuizAppFullStack.repository.QuestionRepository;
import com.dhanush.QuizAppFullStack.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id); // Directly return the Optional
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubjects(); // Fixed method name
    }

    @Override
    @Transactional
    public Question updateQuestion(Long id, Question question) throws ResourceNotFoundException {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

        existingQuestion.setQuestion(question.getQuestion());
        existingQuestion.setChoices(question.getChoices());
        existingQuestion.setCorrectAnswers(question.getCorrectAnswers());
        existingQuestion.setQuestionType(question.getQuestionType());
        existingQuestion.setSubject(question.getSubject());

        return questionRepository.save(existingQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}