package org.example.royaleanalytics.dto.api;

import lombok.Data;

import java.util.Map;

@Data
public class Player {
    private Integer trophies;
    private int currentDeckId;
    private Map<String, Object> rewards;
}
