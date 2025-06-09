package org.example.royaleanalytics.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationRequest {
    private Integer trophies;
    private Integer topN;
}
