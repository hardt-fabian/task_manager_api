package com.hardt_fabian.task_manager_api.dtos.task;

import com.hardt_fabian.task_manager_api.dtos.note.NoteResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Datentransferobjekt einer Task mit Datentransferobjekten von dazugehörigen Notizen
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
public class TaskWithNotesDTO {
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

    /**
     * Datentransferobjekten von Notizen der Task
     */
    private List<NoteResponseDTO> notes;
}
