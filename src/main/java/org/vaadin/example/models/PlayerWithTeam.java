package org.vaadin.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerWithTeam {

    @JsonProperty("PLAYER NAME")
    String playerName;

    @JsonProperty("TEAM")
    String TEAM;

    @JsonProperty("POS")
    String POS;

}
