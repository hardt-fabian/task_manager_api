package com.hardt_fabian.task_manager_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Die Entität {@code Note} repräsentiert eine Notiz, die einer {@link Task} zugeordnet ist.
 * <p>
 * Mehrere Notizen können einer einzelnen Aufgabe ({@code Task}) zugeordnet werden
 * (Many-to-One-Beziehung).
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
public class Note {

    /**
     * Automatisch generierte ID der Notiz.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Titel der Notiz.
     */
    private String title;

    /**
     * Inhalt der Notiz.
     */
    private String body;

    /**
     * Task zu der diese Notiz gehört.
     */
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
