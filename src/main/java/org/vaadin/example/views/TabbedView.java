package org.vaadin.example.views;

import org.vaadin.example.data.PlayerDataService;
import org.vaadin.example.views.tabs.AllPlayersTab;
import org.vaadin.example.views.tabs.DepthChartsTab;
import org.vaadin.example.views.tabs.MyTeamTab;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;

public class TabbedView extends Div {

    public TabbedView(PlayerDataService playerDataService) {

        TabSheet tabs = new TabSheet();

        tabs.addThemeVariants(TabSheetVariant.LUMO_BORDERED);

        tabs.add("All Available Players", new Div(new AllPlayersTab(playerDataService)));

        add(tabs);
    }

}
