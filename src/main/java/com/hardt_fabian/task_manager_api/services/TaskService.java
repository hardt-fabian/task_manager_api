package com.hardt_fabian.task_manager_api.services;

import com.hardt_fabian.task_manager_api.ModelMapperConfig;
import com.hardt_fabian.task_manager_api.SortOrder;
import com.hardt_fabian.task_manager_api.TaskNotFoundException;
import com.hardt_fabian.task_manager_api.controller.TaskController;
import com.hardt_fabian.task_manager_api.dtos.task.TaskResponseDTO;
import com.hardt_fabian.task_manager_api.entities.Task;
import com.hardt_fabian.task_manager_api.repositories.TaskRepository;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Aufgaben.
 * <p>
 * Diese Klasse stellt Geschäftslogik für das Erstellen, Aktualisieren, Sortieren und Lesen von
 * Aufgaben bereit. Sie dient als Vermittler zwischen der Persistenzschicht {@link TaskRepository}
 * und der Präsentationsschicht {@link TaskController}.
 * </p>
 *
 * @author Fabian Hardt
 */
@Service
public class TaskService {
    /**
     * Repository-Schnittstelle für den Zugriff auf {@link Task}-Entitäten
     */
    private final TaskRepository taskRepository;

    /**
     * Mapper um von Objekten eines Typen auf einen anderen zu mappen
     */
    private final ModelMapper modelMapper;

    /**
     * Konstruktor
     *
     * @param taskRepository Repository-Schnittstelle für den Zugriff auf {@link Task}-Entitäten
     * @param modelMapper    Mapper um von Objekten eines Typen auf einen anderen zu mappen
     */
    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gibt die Task-Entity mit der übergebenen taskId zurück.
     *
     * @param taskId ID der gesuchten Task
     * @return Task-Entity mit der übergebenen taskId
     * @throws TaskNotFoundException wenn es keine Task-Entity mit der übergebenen taskId gibt
     */
    Task getTaskEntity(long taskId) {
        return this.taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId));
    }

    /**
     * Gibt alle Aufgaben zurück.
     *
     * @return alle Aufgaben
     */
    public List<TaskResponseDTO> getTasks() {
        List<Task> tasks = this.taskRepository.findAll();
        return ModelMapperConfig.map(this.modelMapper, tasks, TaskResponseDTO.class);
    }

    /**
     * Gibt bei {@code true} alle abgeschlossenen Aufgaben zurück, bei {@code false} alle offenen
     * Aufgaben und bei {@code null} alle Aufgaben.
     *
     * @param completed ob nur die abgeschlossenen Aufgaben angezeigt werden sollen oder nicht
     * @return alle abgeschlossenen Aufgaben, alle offenen Aufgaben oder alle Aufgaben.
     */
    public List<TaskResponseDTO> getTasksByCompleted(Boolean completed) {
        if (completed == null) {
            return this.getTasks();
        }
        List<Task> tasksByCompleted = this.taskRepository.findAllByCompleted(completed);
        return ModelMapperConfig.map(this.modelMapper, tasksByCompleted, TaskResponseDTO.class);
    }

    /**
     * Gibt das Datentransferobjekt der Aufgabe mit der übergebenen ID zurück.
     *
     * @param id ID der gesuchten Aufgaben
     * @return Datentransferobjekt der Aufgabe nit der übergebenen ID
     * @throws TaskNotFoundException wenn keine Aufgabe mit der übergebenen ID gefunden wurde
     */
    public TaskResponseDTO getTaskById(long id) throws TaskNotFoundException {
        Task task = this.taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id));

        return this.modelMapper.map(task, TaskResponseDTO.class);
    }

    /**
     * Gibt ein Datentransferobjekt der erzeugten Aufgabe zurück.
     *
     * @param title       Titel der neuen Aufgabe
     * @param description Beschreibung der neuen Aufgabe
     * @param deadline    Fälligkeitsdatum der Aufgabe
     * @return Datentransferobjekt der erzeugten Aufgabe
     */
    public TaskResponseDTO addTask(String title, String description, LocalDateTime deadline) {
        Task task = new Task(title, description, deadline);
        this.taskRepository.save(task);
        return this.modelMapper.map(task, TaskResponseDTO.class);
    }

    /**
     * Gibt ein Datentransferobjekt der aktualisierten Aufgabe zurück.
     *
     * @param id          ID der Aufgabe welche aktualisiert werden soll
     * @param description neue Beschreibung der Aufgabe oder {@code null} wenn sie nicht geändert
     *                    werden soll
     * @param deadline    neues Fälligkeitsdatum der Aufgabe oder {@code null} wenn es nicht
     *                    geändert werden soll
     * @param completed   neuer Status ob, die Aufgabe offen oder abgeschlossen ist oder
     *                    {@code null} wenn er nicht geändert werden soll
     * @return Datentransferobjekt der erzeugten Aufgabe
     * @throws TaskNotFoundException wenn keine Aufgabe mit der übergebenen ID gefunden wurde
     */
    public TaskResponseDTO updateTaskById(long id, @Nullable String description, @Nullable LocalDateTime deadline, @Nullable Boolean completed) throws TaskNotFoundException {
        Task task = this.taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id));

        if (description != null) {
            task.setDescription(description);
        }
        if (deadline != null) {
            task.setDeadline(deadline);
        }
        if (completed != null) {
            task.setCompleted(completed);
        }
        this.taskRepository.save(task);
        return this.modelMapper.map(task, TaskResponseDTO.class);
    }

    /**
     * Gibt eine Liste von Datentransferobjekt der Aufgaben mit teileweise oder
     * vollständig übereinstimmenden Titeln zurück.
     *
     * @param title Gesuchter Titel
     * @return eine Liste von Datentransferobjekt der Aufgaben mit teileweise oder
     * vollständig übereinstimmenden Titeln
     */
    public List<TaskResponseDTO> getTasksByTitle(String title) {
        List<Task> tasksByTitle = this.taskRepository.getTasksByTitleContainingIgnoreCase(title);
        return ModelMapperConfig.map(this.modelMapper, tasksByTitle, TaskResponseDTO.class);
    }

    /**
     * Gibt Liste aller Aufgaben nach Fälligkeitsdatum sortiert als Datentransferobjekte zurück.
     * Die Sortierung ist abhängig von der übergebenen order.
     *
     * @param order Order, nach welcher die Aufgaben sortiert werden sollen.
     * @return Liste aller Aufgaben nach Fälligkeitsdatum sortiert als Datentransferobjekte
     */
    public List<TaskResponseDTO> getTasksSortedByDeadline(SortOrder order) {
        List<Task> deadLineSortedTasks = switch (order) {
            case ASC -> this.taskRepository.findAllByOrderByDeadlineAsc();
            case DESC -> this.taskRepository.findAllByOrderByDeadlineDesc();
        };
        return ModelMapperConfig.map(this.modelMapper, deadLineSortedTasks, TaskResponseDTO.class);
    }

    /**
     * Löscht die Aufgabe mit der angegeben id.
     *
     * @param id ID der zu löschenden Task
     * @throws TaskNotFoundException wenn keine Aufgabe mit der übergebenen ID gefunden wurde
     */
    public void deleteTaskById(long id) throws TaskNotFoundException {
        Task task =
                this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        this.taskRepository.delete(task);
    }
}
