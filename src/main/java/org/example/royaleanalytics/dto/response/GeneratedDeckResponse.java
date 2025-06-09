package org.example.royaleanalytics.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GeneratedDeckResponse {
    private Integer id;
    private String description;
    private List<Integer> cards;
}