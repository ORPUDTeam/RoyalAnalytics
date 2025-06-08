package org.example.royaleanalytics.dto.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Player {
    private Integer trophies;
    private List<CardApi> deck;
    private Map<String, Object> rewards;
}
