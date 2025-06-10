package org.example.royaleanalytics.dto.response;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RatingHistoryDto {

    private List<RatingChangeDto> ratingChangeDtoList;

}
