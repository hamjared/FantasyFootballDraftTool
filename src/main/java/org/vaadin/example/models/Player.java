package org.vaadin.example.models;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "hasBeenDrafted" })
public class Player {

    @JsonProperty("Name")
    String name;

    @JsonProperty("Position")
    String position;

    @JsonProperty("Proj")
    double projectedPoints;

    @JsonProperty("VAL")
    double val;

    @JsonProperty("Bye")
    int byeWeek;

    @JsonProperty("ADP")
    double averageDraftPosition;

    boolean hasBeenDrafted = false;

}
