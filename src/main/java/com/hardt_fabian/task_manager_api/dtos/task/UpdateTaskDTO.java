package com.hardt_fabian.task_manager_api.dtos.task;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Datentransferobjekt zur Aktualisierung einer Task
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateTaskDTO {

    /**
     * Neue Beschreibung der Task oder {@code null} wenn es keine aktualisierung gibt
     */
    private String description;

    /**
     * Neues Fälligkeitsdatum der Task oder {@code null} wenn es keine aktualisierung gibt
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;

    /**
     * Ob die Task erfüllt oder offen sein soll oder {@code null} wenn es keine aktualisierung gibt
     */
    private Boolean completed;
}
