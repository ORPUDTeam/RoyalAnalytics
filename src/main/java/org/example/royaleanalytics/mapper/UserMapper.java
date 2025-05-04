package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.request.RegistrationFormDTO;
import org.example.royaleanalytics.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    @Mapping(target = "player_tag", source = "playerTag")
    @Mapping(target = "password_hash", ignore = true)
    User toUser(RegistrationFormDTO registrationFormDTO);
}
