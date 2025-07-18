package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.dto.response.CardResponse;
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

    default Card updateCard(Card oldCart, CardApi cardApi){
        oldCart.setUpdated_at(LocalDateTime.now());
        oldCart.setElixir(cardApi.getElixir()!=null?cardApi.getElixir():0);
        oldCart.setRarity(cardApi.getRarity());
        oldCart.setImageUrl(cardApi.getIconUrls().getMediumUrl());
        return oldCart;
    };
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updated_at", expression = "java(LocalDateTime.now())")
    @Mapping(target = "imageUrl", source = "cardApi.iconUrls.mediumUrl")
    Card mapToCard(CardApi cardApi);

    CardResponse convertToResponse(Card card);
}
