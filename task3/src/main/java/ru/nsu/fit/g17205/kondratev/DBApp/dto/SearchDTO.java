package ru.nsu.fit.g17205.kondratev.DBApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
/**
 * ДТО для данных поиска ближайших нодов
 */
public class SearchDTO {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Double radius;

}
