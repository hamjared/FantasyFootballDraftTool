package org.vaadin.example.views;

import org.vaadin.example.data.PlayerDataService;
import org.vaadin.example.views.tabs.AllPlayersTab;
import org.vaadin.example.views.tabs.DepthChartsTab;
import org.vaadin.example.views.tabs.MyTeamTab;
import org.vaadin.example.views.tabs.PlayerQueueTab;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;

public class TabbedView extends Div {

    PlayerQueueTab playerQueueTab = new PlayerQueueTab();

    public TabbedView(PlayerDataService playerDataService) {

        TabSheet tabs = new TabSheet();

        tabs.addThemeVariants(TabSheetVariant.LUMO_BORDERED);

        tabs.add("All Available Players", new Div(new AllPlayersTab(playerDataService, playerQueueTab)));
        tabs.add("My Queue", new Div(playerQueueTab));

        add(tabs);
    }

}
