package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.request.RegistrationFormDTO;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    @Mapping(target = "playerTag", source = "playerTag")
    @Mapping(target = "password_hash", ignore = true)
    User toUser(RegistrationFormDTO registrationFormDTO);


    PlayerDto mapToPlayerDto(User user);
}
