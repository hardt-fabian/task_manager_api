package com.hardt_fabian.task_manager_api.controller;

import com.hardt_fabian.task_manager_api.SortOrder;
import com.hardt_fabian.task_manager_api.TaskNotFoundException;
import com.hardt_fabian.task_manager_api.dtos.task.CreateTaskDTO;
import com.hardt_fabian.task_manager_api.dtos.ErrorResponseDTO;
import com.hardt_fabian.task_manager_api.dtos.task.TaskResponseDTO;
import com.hardt_fabian.task_manager_api.dtos.task.UpdateTaskDTO;
import com.hardt_fabian.task_manager_api.services.NoteService;
import com.hardt_fabian.task_manager_api.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * REST-Controller für Aufgaben.
 * Bietet Endpunkte zum Abrufen aller Aufgaben, zum Hinzufügen einer Task, zum Überarbeiten einer
 * Task, zum Filtern von Aufgaben und zum Sortieren von Aufgaben.
 *
 * @author Fabian Hardt
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /**
     * Schnittstelle zur Business-Logik für Aufgaben
     */
    private final TaskService taskService;
    private final NoteService noteService;

    /**
     * Konstruktor
     *
     * @param taskService Schnittstelle zur Business-Logik für Aufgaben
     */
    public TaskController(TaskService taskService, NoteService noteService) {
        this.taskService = taskService;
        this.noteService = noteService;
    }

    /**
     * Gibt die Task mit der angefragten Id zurück
     *
     * @param id die id der gesuchten Task
     * @return Antwort Entity mit den Status ok und der gesuchten Task
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable long id) {
        TaskResponseDTO taskResponse = this.taskService.getTaskById(id);
        return ResponseEntity.ok(taskResponse);
    }

    /**
     * Fügt eine neu erzeugte Task mit den übergeben Daten hinzu.
     *
     * @param body Daten der neuen Task
     * @return Antwort Entity mit den Status ok und der neuen Task
     * @throws DateTimeParseException wenn ein Problem bei Parsen der Deadline aufritt
     */
    @PostMapping("")
    public ResponseEntity<TaskResponseDTO> addTask(@RequestBody CreateTaskDTO body) throws DateTimeParseException {
        TaskResponseDTO taskResponse = this.taskService.addTask(body.getTitle(),
                body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(taskResponse);
    }

    /**
     * Fügt eine neu erzeugte Task mit den übergeben Daten hinzu.
     *
     * @param id die id der zu aktualisierenden Task
     * @param update die neuen Daten
     * @return ResponseEntity mit den Status ok und der aktualisierten Task
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable long id,
            @RequestBody UpdateTaskDTO update) {
        TaskResponseDTO taskResponse = this.taskService.updateTaskById(id, update.getDescription(),
                update.getDeadline(), update.getCompleted());
        return ResponseEntity.ok(taskResponse);
    }

    /**
     * Liefert eine Liste von Aufgaben, die mit dem übergebenen Titel übereinstimmen.
     *
     * @param title der Titel (oder ein Teil davon), nach dem gesucht wird
     * @return ResponseEntity mit der Liste passender TaskResponseDTOs
     */
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> getTaskByTitle(@RequestParam String title){
        List<TaskResponseDTO> tasksByTitle = this.taskService.getTasksByTitle(title);
        return ResponseEntity.ok(tasksByTitle);
    }

    /**
     * Liefert eine gefilterte Liste von Aufgaben, die abhängig von completed nur erfüllte oder
     * nur offene Aufgaben enthält.
     * Wenn completed {@code null} ist, werden alle Aufgaben zurückgeliefert.
     *
     * @param completed ob nach erfüllten oder offenen Aufgaben gefiltert werden soll
     * @return ResponseEntity mit der Liste passender TaskResponseDTOs
     */
    @GetMapping("")
    public ResponseEntity<List<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) Boolean completed) {
        if (completed == null) {
            List<TaskResponseDTO> tasks = this.taskService.getTasks();
            return ResponseEntity.ok(tasks);
        }
        List<TaskResponseDTO> tasks = this.taskService.getTasksByCompleted(completed);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Gibt eine nach Fälligkeitsdatum sortierte Liste von Task zurück. Wenn keine angabe gemacht
     * wird, wird nach aufsteigenden Daten sortiert. Also vom kleinsten zum größten Wert, beginnend
     * mit dem ältesten Datum.
     *
     * @param order nach welcher Ordnung die Liste sortiert werden soll
     * @return ResponseEntity mit der sortierten Liste passender TaskResponseDTOs
     */
    @GetMapping("/sorted")
    public ResponseEntity<List<TaskResponseDTO>> getTasksSortedByDeadline(
            @RequestParam(defaultValue = "asc") String order) {
        SortOrder sortOrder;
        try {
            sortOrder = SortOrder.fromString(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        List<TaskResponseDTO> tasks = this.taskService.getTasksSortedByDeadline(sortOrder);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Löscht die Aufgabe mit der übergebenen ID.
     *
     * @param id ID der zu löschenden Aufgabe
     * @return ResponseEntity mit Statuscode 204 No Content bei erfolgreicher Löschung
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {
        this.taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Errorhandler
     *
     * @param e ausgelöste Exception
     * @return ResponseEntity mit den entsprechenden Status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e) {
        if (e instanceof DateTimeParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date format"));
        }
        if (e instanceof TaskNotFoundException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
        }
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }
}
