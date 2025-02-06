package com.dhanush.QuizAppFullStack.repository;

import com.dhanush.QuizAppFullStack.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Retrieves a list of distinct subjects from the Question table.
     *
     * @return a list of unique subject names.
     */
    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubjects();

    /**
     * Finds all questions belonging to a specific subject with pagination support.
     *
     * @param subject  the subject to filter questions by.
     * @param pageable pagination details.
     * @return a paginated list of questions for the given subject.
     */
    Page<Question> findBySubject(String subject, Pageable pageable);
}
