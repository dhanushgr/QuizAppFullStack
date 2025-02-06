package com.dhanush.QuizAppFullStack.service;

import com.dhanush.QuizAppFullStack.model.Question;
import com.dhanush.QuizAppFullStack.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // Constructor-based injection
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
        /*
        "save, findAll, findById" and many more methods is present in
        JpaRepository which is extended by QuestionRepository
         */
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubjects(); // Fixed method name
    }

    @Override
    @Transactional
    public Question updateQuestion(Long id, Question question) throws NotFoundException {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);

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
