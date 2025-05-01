package org.example.royaleanalytics.type;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class DataJson implements Serializable {
    private String stringData;
    private Long longData;
    private Map<String, String> customData;
}