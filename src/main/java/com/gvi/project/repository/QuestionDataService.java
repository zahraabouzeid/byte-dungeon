package com.gvi.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service layer for Question data operations.
 * Provides methods to retrieve questions in various formats including the requested Map structure.
 */
@Service
public class QuestionDataService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionDataService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Get all questions as a Map with Map<Question, Answer> as key and List<Possibilities> as value.
     * Structure: Map<Map<Question, Answer>, List<Possibilities>>
     *
     * @return Map where each entry contains a single question-answer pair mapped to its possibilities
     */
    public Map<Map<String, String>, List<String>> getAllQuestionsAsMap() {
        List<QuestionEntity> allQuestions = questionRepository.findAll();
        Map<Map<String, String>, List<String>> result = new LinkedHashMap<>();

        for (QuestionEntity entity : allQuestions) {
            // Create inner map with Question -> Answer
            Map<String, String> questionAnswerMap = new HashMap<>();
            questionAnswerMap.put(entity.getQuestion(), entity.getAnswer());

            // Parse CSV possibilities into List
            List<String> possibilitiesList = parsePossibilities(entity.getPossibilities());

            result.put(questionAnswerMap, possibilitiesList);
        }

        return result;
    }

    /**
     * Alternative: Get as a simpler structure using a custom DTO
     */
    public List<QuestionDTO> getAllQuestionsAsDTOs() {
        return questionRepository.findAll().stream()
                .map(entity -> new QuestionDTO(
                        entity.getQuestion(),
                        entity.getAnswer(),
                        parsePossibilities(entity.getPossibilities())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Get a single question's data as Map structure
     */
    public Optional<Map<Map<String, String>, List<String>>> getQuestionAsMap(Long id) {
        return questionRepository.findById(id)
                .map(entity -> {
                    Map<Map<String, String>, List<String>> result = new HashMap<>();
                    Map<String, String> questionAnswerMap = new HashMap<>();
                    questionAnswerMap.put(entity.getQuestion(), entity.getAnswer());
                    result.put(questionAnswerMap, parsePossibilities(entity.getPossibilities()));
                    return result;
                });
    }

    /**
     * Save a new question from Map structure
     */
    public QuestionEntity saveFromMap(Map<String, String> questionAnswer, List<String> possibilities) {
        if (questionAnswer.isEmpty()) {
            throw new IllegalArgumentException("Question-Answer map cannot be empty");
        }

        Map.Entry<String, String> entry = questionAnswer.entrySet().iterator().next();
        String question = entry.getKey();
        String answer = entry.getValue();
        String possibilitiesCsv = String.join(",", possibilities);

        QuestionEntity entity = new QuestionEntity(question, answer, possibilitiesCsv);
        return questionRepository.save(entity);
    }

    /**
     * Parse CSV string into List of possibilities
     */
    private List<String> parsePossibilities(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    // CRUD operations delegating to repository
    public List<QuestionEntity> findAll() {
        return questionRepository.findAll();
    }

    public Optional<QuestionEntity> findById(Long id) {
        return questionRepository.findById(id);
    }

    public QuestionEntity save(QuestionEntity entity) {
        return questionRepository.save(entity);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    /**
     * DTO class for cleaner data transfer
     */
    public static class QuestionDTO {
        private final String question;
        private final String answer;
        private final List<String> possibilities;

        public QuestionDTO(String question, String answer, List<String> possibilities) {
            this.question = question;
            this.answer = answer;
            this.possibilities = possibilities;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public List<String> getPossibilities() {
            return possibilities;
        }

        @Override
        public String toString() {
            return "QuestionDTO{" +
                    "question='" + question + '\'' +
                    ", answer='" + answer + '\'' +
                    ", possibilities=" + possibilities +
                    '}';
        }
    }
}
