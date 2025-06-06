package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {LocalDateTime.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CardMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Card mapToCard(CardApi cardApi);

}
