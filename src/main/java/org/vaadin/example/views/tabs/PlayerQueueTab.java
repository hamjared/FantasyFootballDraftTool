package org.vaadin.example.views.tabs;

import org.vaadin.example.models.Player;
import java.util.List;
import java.util.ArrayList;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class PlayerQueueTab extends VerticalLayout {

    List<Player> myPlayers = new ArrayList<>();
    ListDataProvider<Player> filteredPlayersProvider;
    Grid<Player> grid;

    private Player draggedItem;

    public PlayerQueueTab() {
        filteredPlayersProvider = new ListDataProvider<Player>(myPlayers);

        createQueueTable();
    }

    private void createQueueTable() {

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

        GridListDataView<Player> dataView = grid.setItems(filteredPlayersProvider);
        grid.setRowsDraggable(true);

        grid.addDragStartListener(e -> {
            draggedItem = e.getDraggedItems().get(0);
            grid.setDropMode(GridDropMode.BETWEEN);
        });

        grid.addDropListener(e -> {
            Player targetPlayer = e.getDropTargetItem().orElse(null);
            GridDropLocation dropLocation = e.getDropLocation();

            boolean personWasDroppedOntoItself = draggedItem
                    .equals(targetPlayer);

            if (targetPlayer == null || personWasDroppedOntoItself)
                return;

            dataView.removeItem(draggedItem);

            if (dropLocation == GridDropLocation.BELOW) {
                dataView.addItemAfter(draggedItem, targetPlayer);
            } else {
                dataView.addItemBefore(draggedItem, targetPlayer);
            }
        });

        grid.addDragEndListener(e -> {
            draggedItem = null;
            grid.setDropMode(null);
        });

        filteredPlayersProvider.setFilter(item -> {
            if (item.isHasBeenDrafted()) {
                return false;
            }

            return true;
        });

        add(grid);

    }

    public void addPlayer(Player player) {
        if (myPlayers.contains(player)) {
            return;
        }
        myPlayers.add(player);
        filteredPlayersProvider.refreshAll();
        grid.recalculateColumnWidths();
    }
}
