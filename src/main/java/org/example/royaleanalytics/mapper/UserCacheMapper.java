package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.entity.UserCache;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserCacheMapper {


    UserCache mapToUserCache(UserCache oldCache, Player player);

    UserCache mapToUserCache(Player player);

}
