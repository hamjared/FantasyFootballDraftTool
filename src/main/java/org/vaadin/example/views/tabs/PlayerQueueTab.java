package org.vaadin.example.views.tabs;

import org.vaadin.example.models.Player;
import java.util.List;
import java.util.ArrayList;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class PlayerQueueTab extends VerticalLayout {

    List<Player> myPlayers = new ArrayList<>();
    ListDataProvider<Player> filteredPlayersProvider;

    public PlayerQueueTab() {
        filteredPlayersProvider = new ListDataProvider<Player>(myPlayers);

        createQueueTable();
    }

    private void createQueueTable() {

        Grid<Player> grid = new Grid<>(Player.class, false);
        grid.addColumn(Player::getName).setHeader("Name").setKey("name");
        grid.addColumn(Player::getPosition).setHeader("Pos").setKey("pos");
        grid.addColumn(Player::getTeam).setHeader("Team").setSortable(true);
        grid.addColumn(Player::getByeWeek).setHeader("Bye").setKey("bye");
        grid.addColumn(Player::getProjectedPoints).setHeader("Proj.").setKey("proj").setSortable(true);
        grid.addColumn(Player::getAverageDraftPosition).setHeader("ADP").setKey("ADP").setSortable(true);

        grid.setDataProvider(filteredPlayersProvider);

        filteredPlayersProvider.setFilter(item -> {
            if (item.isHasBeenDrafted()) {
                return false;
            }

            return true;
        });

        add(grid);

    }

    public void addPlayer(Player player) {
        myPlayers.add(player);
    }
}
