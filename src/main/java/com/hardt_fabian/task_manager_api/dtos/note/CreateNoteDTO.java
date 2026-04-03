package com.hardt_fabian.task_manager_api.dtos.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datentransferobjekt zur Erstellung einer Notiz
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateNoteDTO {
    /**
     * Titel der zu erstellenden Notiz
     */
    private String title;
    /**
     * Inhalt der zu erstellenden Notiz
     */
    private String body;
}
