package org.example.royaleanalytics.openFeign;

import org.example.royaleanalytics.dto.response.RecommendationResponse;
import org.example.royaleanalytics.dto.request.RecommendationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "deckRecommendationClient",
        url = "http://localhost:8000"
)
public interface GeneratedDeckClient {
    @PostMapping(
            value = "/recommend",
            consumes = "application/json",
            produces = "application/json"
    )
    RecommendationResponse getRecommendations(RecommendationRequest request);
}
