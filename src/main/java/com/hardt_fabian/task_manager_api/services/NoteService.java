package com.hardt_fabian.task_manager_api.services;

import com.hardt_fabian.task_manager_api.ModelMapperConfig;
import com.hardt_fabian.task_manager_api.controller.NotesController;
import com.hardt_fabian.task_manager_api.dtos.note.CreateNoteDTO;
import com.hardt_fabian.task_manager_api.dtos.note.NoteResponseDTO;
import com.hardt_fabian.task_manager_api.entities.Note;
import com.hardt_fabian.task_manager_api.entities.Task;
import com.hardt_fabian.task_manager_api.repositories.NoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Notizen.
 * <p>
 * Diese Klasse stellt Geschäftslogik für das Erstellen und Lesen von Notizen bereit.
 * Sie dient als Vermittler zwischen der Persistenzschicht {@link NoteRepository}
 * und der Präsentationsschicht {@link NotesController}.
 * </p>
 *
 * @author Fabian Hardt
 */
@Service
public class NoteService {

    /**
     * Verbindung zur Service-Klasse zur Verwaltung von Aufgaben
     */
    private final TaskService taskService;

    /**
     * Repository-Schnittstelle für den Zugriff auf {@link Note}-Entitäten
     */
    private final NoteRepository noteRepository;

    /**
     * Mapper um von Objekten eines Typen auf einen anderen zu mappen
     */
    private final ModelMapper modelMapper;

    /**
     * Konstruktor
     *
     * @param taskService Verbindung zur Service-Klasse zur Verwaltung von Aufgaben
     * @param noteRepository Repository-Schnittstelle für den Zugriff auf {@link Note}-Entitäten
     * @param modelMapper Mapper um von Objekten eines Typen auf einen anderen zu mappen
     */
    public NoteService(TaskService taskService, NoteRepository noteRepository, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gibt die zugehörigen Notizen für die Aufgabe mit der übergebenen ID, als NoteResponseDTO,
     * zurück.
     *
     * @param taskId ID der Aufgaben
     * @return Notizen für die Aufgabe mit der übergebenen ID als Liste von NoteResponseDTO
     */
    public List<NoteResponseDTO> getNotesForTask(long taskId) {
        Task task = this.taskService.getTaskEntity(taskId);
        List<Note> notes = task.getNotes();
        notes.forEach(note -> System.out.println(note.getTitle()));
        List<NoteResponseDTO> map = ModelMapperConfig.map(this.modelMapper, notes, NoteResponseDTO.class);
        map.forEach(noteResponseDTO -> System.out.println(noteResponseDTO));
        return map;
    }

    /**
     * Fügt eine neue Notiz aus den übergebenen Datentransferobjekt zu der Task mit der übergebenen
     * ID hinzu.
     *
     * @param taskId ID der Aufgabe zu der die Notiz hinzugefügt werden soll
     * @param createNoteDTO Datentransferobjekt zur Erstellung der Notiz
     * @return NoteResponesDTO der neuen Notiz
     */
    public NoteResponseDTO addNoteForTask(long taskId, CreateNoteDTO createNoteDTO) {
        Task task = this.taskService.getTaskEntity(taskId);
        Note note = new Note();

        note.setTitle(createNoteDTO.getTitle());
        note.setBody(createNoteDTO.getBody());
        note.setTask(task);
        task.getNotes().add(note);
        this.noteRepository.save(note);
        return this.modelMapper.map(note, NoteResponseDTO.class);
    }
}
