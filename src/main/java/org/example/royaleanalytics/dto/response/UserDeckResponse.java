package org.example.royaleanalytics.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserDeckResponse {
    private Integer id;
    private String name;
    private boolean status;
    private List<Integer> cards;

}
