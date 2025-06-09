package org.example.royaleanalytics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class DeckCreateRequest {
    @NotBlank(message = "Deck name is required")
    private String name;

    @Size(min = 8, max = 8, message = "Deck must contain exactly 8 cards")
    private List<Integer> cards;
}