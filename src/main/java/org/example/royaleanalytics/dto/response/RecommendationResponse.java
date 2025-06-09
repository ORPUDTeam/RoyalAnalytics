package org.example.royaleanalytics.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private List<List<String>> decks;
}
