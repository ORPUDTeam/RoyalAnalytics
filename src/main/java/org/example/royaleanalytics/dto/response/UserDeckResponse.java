package org.example.royaleanalytics.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserDeckResponse {
    private Integer id;
    private String name;
    private boolean status;
    private List<Integer> cards;

}
