package com.hardt_fabian.task_manager_api.dtos.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datentransferobjekt einer erstellten Notiz zu einer Aufgabe
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteResponseDTO {
    /**
     * ID der Task zu der diese Notiz gehört
     */
    private long taskId;

    /**
     * Notiz
     */
    private NoteResponseDTO note;
}
