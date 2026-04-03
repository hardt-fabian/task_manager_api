package com.hardt_fabian.task_manager_api.dtos.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {
    private long id;
    private String title;
    private String body;
}



