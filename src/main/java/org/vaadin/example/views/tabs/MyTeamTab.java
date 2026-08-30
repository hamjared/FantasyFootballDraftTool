package org.vaadin.example.views.tabs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.data.PlayerDataService;
import org.vaadin.example.models.Player;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class MyTeamTab extends VerticalLayout {

    List<Player> myPlayers = new ArrayList<>();
    ListDataProvider<Player> myPlayersProvider;
    Grid<Player> grid;

    public MyTeamTab(@Autowired PlayerDataService playerDataService) {
        for (Player player : playerDataService.getPlayers()) {
            if (player.isOnMyTeam()) {
                myPlayers.add(player);
            }
        }

        myPlayersProvider = new ListDataProvider<>(myPlayers);

        createTeamTable();
    }

    private void createTeamTable() {
        grid = new Grid<>(Player.class, false);
        grid.addColumn(Player::getName).setHeader("Name").setKey("name").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Player::getPosition).setHeader("Pos").setKey("pos").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Player::getTeam).setHeader("Team").setSortable(true).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Player::getByeWeek).setHeader("Bye").setKey("bye").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(Player::getProjectedPoints).setHeader("Proj.").setKey("proj").setSortable(true).setAutoWidth(true)
                .setFlexGrow(0);
        grid.addColumn(Player::getAverageDraftPosition).setHeader("ADP").setKey("ADP").setSortable(true)
                .setAutoWidth(true).setFlexGrow(0);

        grid.setWidthFull();
        grid.setHeight("70vh");

        grid.setDataProvider(myPlayersProvider);
        grid.recalculateColumnWidths();

        add(grid);
    }

    public void addPlayer(Player player) {
        if (myPlayers.contains(player)) {
            return;
        }
        myPlayers.add(player);
        myPlayersProvider.refreshAll();
        grid.recalculateColumnWidths();
    }

    public void removePlayer(Player player) {
        myPlayers.remove(player);
        myPlayersProvider.refreshAll();
        grid.recalculateColumnWidths();
    }
}
