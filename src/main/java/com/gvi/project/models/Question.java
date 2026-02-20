package com.gvi.project.models;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
/**
 * Entity-Klasse fuer Fragen in der Datenbank
 * Speichert Fragen mit Antworten und moeglichen Antwortoptionen
 */
@Entity
@Table(name = "questions")
public class Question {
    // Primaerschluessel mit automatischer Generierung
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Die Fragestellung
    @Column(name = "question", nullable = false)
    private String question;
    // Die richtige Antwort
    @Column(name = "answer", nullable = false)
    private String answer;
    // Antwortmoeglichkeiten als CSV-String in der Datenbank
    @Column(name = "possibilities", nullable = false)
    private String possibilities;
    // Standard-Konstruktor (erforderlich fuer JPA)
    public Question() {
    }
    /**
     * Konstruktor mit allen Feldern
     * @param question Die Fragestellung
     * @param answer Die richtige Antwort
     * @param possibilities Antwortmoeglichkeiten als CSV-String
     */
    public Question(String question, String answer, String possibilities) {
        this.question = question;
        this.answer = answer;
        this.possibilities = possibilities;
    }
    // Getter und Setter
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
    /**
     * Gibt den rohen CSV-String der Moeglichkeiten zurueck
     * @return Moeglichkeiten als CSV-String
     */
    public String getPossibilities() {
        return possibilities;
    }
    public void setPossibilities(String possibilities) {
        this.possibilities = possibilities;
    }
    /**
     * Konvertiert den CSV-String der Moeglichkeiten in eine Liste
     * @return Liste der Antwortmoeglichkeiten
     */
    public List<String> getPossibilitiesAsList() {
        // CSV-String aufteilen und Leerzeichen entfernen
        if (possibilities == null || possibilities.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(possibilities.split(","))
                .map(String::trim)
                .toList();
    }
    /**
     * Setzt die Moeglichkeiten aus einer Liste (konvertiert zu CSV)
     * @param possibilitiesList Liste der Antwortmoeglichkeiten
     */
    public void setPossibilitiesFromList(List<String> possibilitiesList) {
        this.possibilities = String.join(",", possibilitiesList);
    }
}
