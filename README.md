# Task Manager API

Eine RESTful API zur Verwaltung von Tasks und zugehörigen Notes, entwickelt mit Spring Boot.

## Features

* CRUD-Operationen für Tasks
* Notes können Tasks zugeordnet werden (1:n Beziehung)
* Filterung (z. B. nach Status)
* Sortierung (z. B. nach Deadline)
* DTO-basierte API (keine Entity-Leaks)
* Saubere Layered Architecture (Controller → Service → Repository)
* ModelMapper für automatisches Mapping
* Exception Handling (z. B. 404 bei nicht vorhandenen Ressourcen)

## Architektur

```
Controller → Service → Repository → Database
```

* **Controller**: HTTP-Handling und Endpoints
* **Service**: Business-Logik und Mapping
* **Repository**: Datenzugriff mit Spring Data JPA

## Technologien

* Java
* Spring Boot
* Spring Data JPA
* ModelMapper
* Maven

## API Endpoints

### Tasks

| Methode | Endpoint    | Beschreibung        |
|---------| ----------- |---------------------|
| GET     | /tasks      | Alle Tasks abrufen  |
| GET     | /tasks/{id} | Task nach ID        |
| POST    | /tasks      | Neue Task erstellen |
| PUT     | /tasks/{id} | Task aktualisieren  |
| DELETE  | /tasks/{id} | Task löschen        |

### Notes

| Methode | Endpoint              | Beschreibung             |
| ------- | --------------------- | ------------------------ |
| GET     | /tasks/{taskId}/notes | Notes einer Task abrufen |
| POST    | /tasks/{taskId}/notes | Note zu Task hinzufügen  |

## Beispiel Request

```json
POST /tasks

{
  "title": "Einkaufen",
  "description": "Milch und Brot kaufen",
  "deadline": "2026-03-30T18:00:00"
}
```

## Beispiel Response

```json
{
  "id": 1,
  "title": "Einkaufen",
  "description": "Milch und Brot kaufen",
  "deadline": "2026-03-30T18:00:00",
  "completed": false
}
```

## Setup

1. Repository klonen
2. Maven Build ausführen:

   ```
   mvn clean install
   ```
3. Anwendung starten:

   ```
   mvn spring-boot:run
   ```

## Ziel des Projekts

Dieses Projekt dient zur Demonstration von:

* sauberer Backend-Architektur
* REST API Design
* Umgang mit DTOs und Mapping
* Best Practices in Spring Boot

## Autor

Fabian Hardt
