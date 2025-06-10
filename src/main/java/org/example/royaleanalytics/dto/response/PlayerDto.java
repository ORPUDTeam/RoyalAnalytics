package org.example.royaleanalytics.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
public class PlayerDto {
    private String name;
    private Integer trophies;
    private UserDeckResponse current_deck;
    private Map<String, Object> rewards;
    private LocalDateTime registered_at;
}

