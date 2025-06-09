package org.example.royaleanalytics.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GeneratedDeckRequest {

    @Min(value = 0, message = "рейтинг должен быть положительным")
    private Integer rating;

}
