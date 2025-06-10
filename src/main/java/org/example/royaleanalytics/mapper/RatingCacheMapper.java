package org.example.royaleanalytics.mapper;

import org.example.royaleanalytics.dto.response.RatingChangeDto;
import org.example.royaleanalytics.dto.response.RatingHistoryDto;
import org.example.royaleanalytics.entity.RatingCache;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RatingCacheMapper {

    RatingChangeDto mapToRatingChangeDto(RatingCache ratingCache);
}
