package com.hardt_fabian.task_manager_api.dtos;

import lombok.*;

/**
 * Datentransferobjekt zur Darstellung eines Fehlers
 *
 * @author Fabian Hardt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    /**
     * Fehlernachricht
     */
    private String errorMessage;
}
