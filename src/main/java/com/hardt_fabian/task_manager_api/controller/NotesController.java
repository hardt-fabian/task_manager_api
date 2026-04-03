package com.hardt_fabian.task_manager_api.controller;

import com.hardt_fabian.task_manager_api.dtos.note.CreateNoteDTO;
import com.hardt_fabian.task_manager_api.dtos.note.CreateNoteResponseDTO;
import com.hardt_fabian.task_manager_api.dtos.note.NoteResponseDTO;
import com.hardt_fabian.task_manager_api.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für Notiz von Aufgaben.
 * Bietet Endpoints zum Abrufen aller Notiz einer Aufgabe
 * und zum Hinzufügen neuer Notes.
 *
 * @author Fabian Hardt
 */
@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NotesController {

    /**
     * Schnittstelle zur Business-Logik für Notizen
     */
    private final NoteService noteService;

    /**
     * Konstruktor
     *
     * @param noteService Schnittstelle zur Business-Logik für Notizen
     */
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Liefert alle Notizen der angegebenen Aufgabe.
     *
     * @param taskId ID der Task
     * @return Liste der Notizen als DTOs oder den status NOT_FOUND wenn die Task nicht existiert
     */
    @GetMapping("")
    public ResponseEntity<List<NoteResponseDTO>> getNotes(@PathVariable long taskId) {
        List<NoteResponseDTO> notesForTask = this.noteService.getNotesForTask(taskId);

        if (notesForTask == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notesForTask);
    }

    /**
     * Fügt eine neue Notiz zur angegebenen Aufgabe hinzu.
     *
     * @param taskId ID der Aufgabe zu der die Note hinzugefügt werden soll
     * @param body DTO mit Title und Body der Note
     * @return DTO mit TaskId und Note-Daten
     */
    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDTO> addNote(@PathVariable long taskId,
            @RequestBody CreateNoteDTO body) {
        NoteResponseDTO note = this.noteService.addNoteForTask(taskId, body);
        CreateNoteResponseDTO dto = new CreateNoteResponseDTO(taskId, note);
        return ResponseEntity.ok(dto);
    }
}
