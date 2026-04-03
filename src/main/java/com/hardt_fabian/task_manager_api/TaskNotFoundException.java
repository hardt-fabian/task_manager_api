package com.hardt_fabian.task_manager_api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception zum Anzeigen, dass die Aufgabe mit der gesuchten ID nicht gefunden werden konnte.
 *
 * @author Fabian Hardt
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

    /**
     * Konstruktor
     *
     * @param id ID der nicht nicht existierenden Aufgabe
     */
    public TaskNotFoundException(long id) {
        super("Task with id: " + id + "  not found");
    }
}
