package org.vaadin.example.views.tabs;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.data.PlayerDataService;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MyTeamTab extends VerticalLayout {
    PlayerDataService playerDataService;

    public MyTeamTab(@Autowired PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;

    }
}
