package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.request.RegistrationFormDTO;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    @Mapping(target = "playerTag", source = "playerTag")
    @Mapping(target = "password_hash", ignore = true)
    User toUser(RegistrationFormDTO registrationFormDTO);


    default PlayerDto mapToPlayerDto(User user, UserCache userCache, UserDeck userDeck){
        PlayerDto playerDto = new PlayerDto()
                .setName(user.username)
                .setTrophies(userCache.getTrophies())
                .setRewards(userCache.getRewards())
                .setRegistered_at(user.registered_at);
        UserDeckResponse userDeckResponse = new UserDeckResponse()
                .setId(userDeck.getId())
                .setStatus(userDeck.getStatus())
                .setName(userDeck.getName())
                .setCards(userDeck.getCards().stream().map(Card::getId).toList());
        playerDto.setCurrent_deck(userDeckResponse);
        return playerDto;
    }
}
