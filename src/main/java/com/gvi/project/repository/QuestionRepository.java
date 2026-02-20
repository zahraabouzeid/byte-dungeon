package com.gvi.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for QuestionEntity database operations.
 * Spring Data JPA automatically provides implementations for standard CRUD operations.
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    /**
     * Find a question by its text
     */
    Optional<QuestionEntity> findByQuestion(String question);

    /**
     * Find all questions containing a specific keyword
     */
    List<QuestionEntity> findByQuestionContainingIgnoreCase(String keyword);

    /**
     * Find all questions with a specific answer
     */
    List<QuestionEntity> findByAnswer(String answer);

    /**
     * Custom query to find questions where possibilities contain a specific option
     */
    @Query("SELECT q FROM QuestionEntity q WHERE q.possibilities LIKE %:option%")
    List<QuestionEntity> findByPossibilitiesContaining(@Param("option") String option);

    /**
     * Get all questions ordered by id
     */
    List<QuestionEntity> findAllByOrderByIdAsc();
}
