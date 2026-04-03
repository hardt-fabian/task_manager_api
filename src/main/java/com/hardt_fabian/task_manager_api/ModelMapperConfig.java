package com.hardt_fabian.task_manager_api;

import com.hardt_fabian.task_manager_api.dtos.task.TaskWithNotesDTO;
import com.hardt_fabian.task_manager_api.entities.Task;

import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.builder.ConfigurableConditionExpression;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Konfiguration für den {@link ModelMapper}.
 * <p>
 * Stellt eine zentral konfigurierte Bean für das Mapping zwischen
 * Entities und DTOs bereit. Enthält außerdem ein Custom Mapping
 * für Task → TaskWithNotesDTO sowie eine Hilfsmethode zum Mapping von Listen.
 *
 * @author Fabian Hardt
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Erstellt und konfiguriert den ModelMapper.
     * <p>
     * - STRICT Matching für exaktere Feldzuordnung
     * - Ignoriert null-Werte beim Mapping
     * - Definiert Custom Mapping für Task → TaskWithNotesDTO
     *
     * @return konfigurierte ModelMapper-Instanz
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // Custom Mapping für Task → TaskWithNotesDTO
        mapper.typeMap(Task.class, TaskWithNotesDTO.class).addMappings(
                m ->
                        m.map(Task::getNotes, TaskWithNotesDTO::setNotes));

        return mapper;
    }

    /**
     * Hilfsmethode zum Mapping einer Liste von Objekten auf eine Liste von Datatransferobjekten.
     *
     * @param mapper ModelMapper-Instanz
     * @param sources Quellliste (z. B. Entities)
     * @param dtoClass Zielklasse (DTO)
     * @param <S> Typ der Quelle
     * @param <D> Typ des Ziel-DTOs
     * @return Liste gemappter DTOs
     */
    public static <S,D> List<D> map(ModelMapper mapper, List<S> sources, Class<? extends D> dtoClass) {
        List<D> dList = new ArrayList<>();
        for (S source : sources) {
            D dto = mapper.map(source, dtoClass);
            dList.add(dto);
        }
        return dList;
    }
}
