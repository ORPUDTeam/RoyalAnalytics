package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserCacheMapper {


    default UserCache mapToUserCache(UserCache oldCache, Player player){
        oldCache.setTrophies(player.getTrophies());
        oldCache.setRewards(player.getRewards());
        return oldCache;
    }

    @Mapping(target = "trophies", source = "player.trophies")
    @Mapping(target = "rewards", source = "player.rewards")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    UserCache mapToUserCache(Player player);

    default PlayerDto mapToPlayerDto(UserCache userCache){
        UserDeck userDeck = userCache.getUserDeck();
        PlayerDto playerDto = new PlayerDto()
                .setName(userCache.getPlayer_tag())
                .setTrophies(userCache.getTrophies())
                .setRewards(userCache.getRewards())
                .setRegistered_at(userCache.getUpdatedAt());
        UserDeckResponse userDeckResponse = new UserDeckResponse()
                .setId(userDeck.getId())
                .setStatus(userDeck.getStatus())
                .setName(userDeck.getName())
                .setCards(userDeck.getCards().stream().map(Card::getId).toList());
        playerDto.setCurrent_deck(userDeckResponse);
        return playerDto;
    }
}
