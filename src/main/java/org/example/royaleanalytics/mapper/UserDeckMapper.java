package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.dto.request.DeckCreateRequest;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.UserDeck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserDeckMapper {

    @Mapping(target = "cards",source = "cards",qualifiedByName = "mapCardSetToIndexesList")
    UserDeckResponse toResponse(UserDeck userDeck);

    @Named("mapCardSetToIndexesList")
    default List<Integer> mapCardSetToIndexesList(Set<Card> cards){
        return cards.stream()
                .map(Card::getId)
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    UserDeck toUserDeck(DeckCreateRequest request);

    UserDeck mapToUserDeck(List<CardApi> cards, User user);
}
