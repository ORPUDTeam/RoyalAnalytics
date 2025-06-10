package org.example.royaleanalytics.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingChangeDto {

    private Integer rating;
    private LocalDateTime changedAt;
}
