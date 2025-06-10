package org.example.royaleanalytics.client;


import org.example.royaleanalytics.dto.api.CardsApi;
import org.example.royaleanalytics.dto.api.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.example.royaleanalytics.dto.api.CardApi;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "${api.url}", name = "clashRoyaleApiClient")
public interface ClashRoyaleApiClient {

    @GetMapping(
            value = "${api.cards-path}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization=Bearer ${api.token}"}
    )
    ResponseEntity<CardsApi> getCards();

    @GetMapping(
            value = "${api.users-path}/%23{tag}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization=Bearer ${api.token}"}
    )
    ResponseEntity<Player> getPlayerInfo(@PathVariable("tag") String tag);
}
