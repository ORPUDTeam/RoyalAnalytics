package org.example.royaleanalytics.dto.api;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Player {
    private Integer trophies;
    private Set<CardApi> deck;
    private Map<String, Object> rewards;
}
