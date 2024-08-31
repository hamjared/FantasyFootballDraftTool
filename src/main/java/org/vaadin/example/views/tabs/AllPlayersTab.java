package org.vaadin.example.views.tabs;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.data.PlayerDataService;
import org.vaadin.example.models.Player;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Arrays;

@Slf4j
public class AllPlayersTab extends VerticalLayout {

    PlayerDataService playerDataService;

    List<Player> allPlayers;
    List<Player> filteredPlayers;

    ListDataProvider<Player> filteredPlayersProvider;

    boolean showDrafted = false;
    String showPosition = "All";
    String searchText = "";

    public AllPlayersTab(@Autowired PlayerDataService playerDataService) {
        this.playerDataService = playerDataService;

        this.allPlayers = playerDataService.getPlayers();
        filteredPlayers = allPlayers;
        filteredPlayersProvider = new ListDataProvider<>(allPlayers);

        createPlayerTable();
    }

    private void createPlayerTable() {

        RadioButtonGroup<String> filterButtonGroup = new RadioButtonGroup<>();
        filterButtonGroup.setLabel("Position Filter");
        filterButtonGroup.setItems("All", "QB", "RB", "TE", "K", "DST");
        filterButtonGroup.setValue("All");

        RadioButtonGroup<String> draftedFilterGroup = new RadioButtonGroup<>();
        draftedFilterGroup.setLabel("Drafted Filter");
        draftedFilterGroup.setItems("Drafted", "Un-Drafted");
        draftedFilterGroup.setValue("Un-Drafted");

        TextField searchPlayersField = new TextField("Search");
        searchPlayersField.setClearButtonVisible(true);

        add(filterButtonGroup);
        add(draftedFilterGroup);
        add(searchPlayersField);

        Grid<Player> grid = new Grid<>(Player.class, false);
        grid.addColumn(Player::getName).setHeader("Name").setKey("name");
        grid.addColumn(Player::getPosition).setHeader("Pos").setKey("pos");
        grid.addColumn(Player::getByeWeek).setHeader("Bye").setKey("bye");
        grid.addColumn(Player::getProjectedPoints).setHeader("Proj.").setKey("proj").setSortable(true);
        grid.addColumn(Player::getAverageDraftPosition).setHeader("ADP").setKey("ADP").setSortable(true);

        grid.setDataProvider(filteredPlayersProvider);

        filterButtonGroup.addValueChangeListener(event -> {
            showPosition = event.getValue();
            onFilterChange();
        });

        draftedFilterGroup.addValueChangeListener(event -> {
            showDrafted = event.getValue().equals("Drafted");
            onFilterChange();
        });

        searchPlayersField.setValueChangeMode(ValueChangeMode.EAGER);
        searchPlayersField.addValueChangeListener(event -> {
            searchText = event.getValue();
            onFilterChange();
        });

        HorizontalLayout horizLayout = new HorizontalLayout();

        Button setPlayerDraftedButton = new Button("Set Player Drafted");
        setPlayerDraftedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        setPlayerDraftedButton.addClickListener(clickEvent -> {
            setPlayersDrafted(grid.getSelectedItems());
            grid.getDataProvider().refreshAll();
        });

        Button draftToMyTeamButton = new Button("Draft to My Team");
        draftToMyTeamButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        draftToMyTeamButton.addClickListener(clickEvent -> {
            setPlayersDrafted(grid.getSelectedItems());
            grid.getDataProvider().refreshAll();
        });

        Button undraftButton = new Button("Undraft Player");
        undraftButton.addClickListener(clickEvent -> {
            setPlayersUnDrafted(grid.getSelectedItems());
            grid.getDataProvider().refreshAll();
        });

        horizLayout.add(setPlayerDraftedButton, draftToMyTeamButton, undraftButton);

        onFilterChange();

        add(grid, horizLayout);
    }

    private void onFilterChange() {
        filteredPlayersProvider.setFilter(item -> {
            boolean draftedFilterMatch = true;
            boolean positionFilterMatch = true;
            boolean searchMatches = true;

            if (showDrafted) {
                draftedFilterMatch = item.isHasBeenDrafted();
            } else {
                draftedFilterMatch = !item.isHasBeenDrafted();
            }

            if (showPosition.equals("All")) {
                positionFilterMatch = true;
            } else {
                positionFilterMatch = showPosition.equals(item.getPosition());
            }

            if (!searchText.trim().isEmpty()) {
                searchMatches = Arrays.asList(searchText.split(" ")).stream()
                        .anyMatch(text -> item.getName().toLowerCase().contains(text.toLowerCase()));
            }

            return draftedFilterMatch && positionFilterMatch && searchMatches;
        });
    }

    private void setPlayersDrafted(Set<Player> players) {
        if (players == null || players.isEmpty()) {
            return;
        }

        for (Player player : players) {
            player.setHasBeenDrafted(true);
            playerDataService.setPlayerDrafted(player, true);
        }

    }

    private void setPlayersUnDrafted(Set<Player> players) {
        if (players == null || players.isEmpty()) {
            return;
        }

        for (Player player : players) {
            player.setHasBeenDrafted(false);
            playerDataService.setPlayerDrafted(player, false);
        }

    }

}
