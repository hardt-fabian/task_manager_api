package com.hardt_fabian.task_manager_api.repositories;

import com.hardt_fabian.task_manager_api.entities.Note;
import com.hardt_fabian.task_manager_api.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository-Interface für den Zugriff auf {@link Task}-Entitäten.
 * <p>
 * Dieses Interface erweitert {@link JpaRepository} und stellt
 * grundlegende CRUD-Operationen für {@link Task}-Objekte bereit.
 * Sowie weitere Operationen zum Sortieren von Aufgaben und finden von Task nach bestimmten
 * Suchkriterien wie Status und Titel.
 * </p>
 *
 * @author Fabian Hardt
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Gibt eine Liste an Task zurück die mit dem übergebenen Titel teilweise oder ganz
     * übereinstimmen.
     *
     * @param title der Titel (oder ein Teil davon), nach dem gesucht wird
     * @return Liste von Task die mit dem Titel teilweise oder ganz übereinstimmen
     */
    List<Task> getTasksByTitleContainingIgnoreCase(String title);

    /**
     * Gibt alle Aufgaben aufsteigend sortiert nach ihrem Fälligkeitsdatum zurück.
     * Also vom kleinsten zum größten Wert, beginnend mit dem ältesten Datum.
     *
     * @return alle Aufgaben aufsteigend sortiert nach ihrem Fälligkeitsdatum
     */
    List<Task> findAllByOrderByDeadlineAsc();

    /**
     * Gibt alle Aufgaben absteigend sortiert nach ihrem Fälligkeitsdatum zurück.
     * Also vom größten zum kleinsten Wert, beginnend mit dem jüngsten Datum.
     *
     * @return alle Aufgaben aufsteigend sortiert nach ihrem Fälligkeitsdatum
     */
    List<Task> findAllByOrderByDeadlineDesc();

    /**
     * Gibt eine gefilterte Liste aller Aufgaben zurück, abhängig davon, ob sie offen (completed =
     * {@code false}) oder abgeschlossen (completed = {@code true}) sein sollen.
     *
     * @param completed ob offene oder abgeschlossene Aufgaben gesucht werden
     * @return eine gefilterte Liste aller Aufgaben, abhängig von completed
     */
    List<Task> findAllByCompleted(boolean completed);
}
