package com.hardt_fabian.task_manager_api.repositories;


import com.hardt_fabian.task_manager_api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository-Interface für den Zugriff auf {@link Note}-Entitäten.
 * <p>
 * Dieses Interface erweitert {@link JpaRepository} und stellt
 * grundlegende CRUD-Operationen für {@link Note}-Objekte bereit.
 * </p>
 *
 * @author Fabian Hardt
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {}
