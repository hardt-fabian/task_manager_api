package com.hardt_fabian.task_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse der Spring Boot Anwendung.
 *
 * @author Fabian Hardt
 */
@SpringBootApplication
public class TaskManagerApiApplication {

    /**
     * Mainmethode
     *
     * @param args Startargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApiApplication.class, args);
    }

}
