package com.hardt_fabian.task_manager_api.dtos.task;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Datentransferobjekt einer Aufgabe
 *
 * @author Fabian Hardt
 */
@NoArgsConstructor
@Getter
@Setter
public class TaskResponseDTO {
    /**
     * ID der Task
     */
    private long id;
    /**
     * Titel der Task
     */
    private String title;
    /**
     * Beschreibung der Task
     */
    private String description;
    /**
     * Fälligkeitsdatum der Task
     */
    private LocalDateTime deadline;
    /**
     * Ob die Task erfüllt oder offen ist
     */
    private boolean completed;
}
