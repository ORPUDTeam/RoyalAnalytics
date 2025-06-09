package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.response.GeneratedDeckResponse;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.GeneratedDeck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface GeneratedDeckMapper {

    @Mapping(target = "cards", source = "cards",qualifiedByName = "mapCardSetToIndexesList")
    GeneratedDeckResponse toGeneratedDeckResponse(GeneratedDeck generatedDeck);

    @Named("mapCardSetToIndexesList")
    default List<Integer> mapCardSetToIndexesList(Set<Card> cards){
        return cards.stream()
                .map(Card::getId)
                .toList();
    }
}
