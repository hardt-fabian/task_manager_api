package com.hardt_fabian.task_manager_api.dtos.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Datentransferobjekt zur Erstellung einer Aufgabe
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateTaskDTO {
    /**
     * Titel der zu erstellenden Aufgabe
     */
    private String title;
    /**
     * Beschreibung der zu erstellenden Aufgabe
     */
    private String description;

    /**
     * Fälligkeitsdatum der zu erstellenden Aufgabe nach ISO-8601 Format
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;
}
