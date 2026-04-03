package com.hardt_fabian.task_manager_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Entität {@code Task} repräsentiert eine Aufgabe.
 * <p>
 * Einer Aufgabe kann mehrere Notizen zugeordnet werden (One-to-Many-Beziehung).
 * </p>
 *
 * <p>
 * Diese Klasse wird als JPA-Entität verwendet und in der Datenbank persistiert.
 * </p>
 *
 * @author Fabian Hardt
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    /**
     * Automatisch generierte ID der Aufgabe
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Titel der Aufgabe
     */
    private String title;

    /**
     * Beschreibung der Aufgabe
     */
    private String description;

    /**
     * Fälligkeitsdatum der Aufgabe
     */
    private LocalDateTime deadline;

    /**
     * Ob die Aufgabe abgeschlossen oder offen ist.
     * Standardmäßig sind Aufgaben offen.
     */
    private boolean completed = false;

    /**
     * Notizen die zu dieser Aufgabe gehören
     */
    @OneToMany(mappedBy = "task",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Note> notes;

    /**
     * Konstruktor für eine Aufgabe
     *
     * @param title Titel der Aufgabe
     * @param description Beschreibung der Aufgabe
     * @param deadline Fälligkeitsdatum
     */
    public Task(String title, String description, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.notes = new ArrayList<>();
    }
}
