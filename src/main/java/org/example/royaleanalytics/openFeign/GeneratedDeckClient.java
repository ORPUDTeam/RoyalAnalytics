package org.example.royaleanalytics.openFeign;

import org.example.royaleanalytics.dto.response.RecommendationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "deckRecommendationClient",
        url = "http://ml-service:8000"
)
public interface GeneratedDeckClient {
    @PostMapping(
            value = "/recommend",
            consumes = "application/json",
            produces = "application/json"
    )
    RecommendationResponse getRecommendations(@RequestParam("trophies") int rating);
}
