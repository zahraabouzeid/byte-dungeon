package com.gvi.project.repository;

import jakarta.persistence.*;

/**
 * JPA Entity representing a Question in the database.
 * Maps to a table with columns: question, answer, possibilities (CSV format)
 */
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "possibilities")
    private String possibilities; // CSV format: "A,B,C,D"

    public QuestionEntity() {
    }

    public QuestionEntity(String question, String answer, String possibilities) {
        this.question = question;
        this.answer = answer;
        this.possibilities = possibilities;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(String possibilities) {
        this.possibilities = possibilities;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", possibilities='" + possibilities + '\'' +
                '}';
    }
}
