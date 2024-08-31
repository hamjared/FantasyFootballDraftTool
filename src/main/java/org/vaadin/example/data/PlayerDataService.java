package org.vaadin.example.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.vaadin.example.models.Player;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helger.commons.io.resource.ClassPathResource;

import lombok.extern.slf4j.Slf4j;
import java.io.File;

@Slf4j
@Service
public class PlayerDataService {

    private static final String[] fileNames = { "QBs.json", "RBs.json", "WRs.json", "TEs.json", "Ks.json",
            "DSTs.json" };

    private static final String saveFileName = System.getProperty("user.dir") + "/" + "playersMap.json";

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Map<Player, Player> playersMap = null;

    public List<Player> getPlayers() {

        if (playersMap != null) {
            return new ArrayList<>(playersMap.values());
        }

        List<Player> players = loadFromFile();

        if (players == null) {
            players = new ArrayList<>();
            for (String fileName : fileNames) {
                InputStream is = new ClassPathResource("classpath:players/" + fileName).getInputStream();

                Player[] playersArray;
                try {
                    playersArray = objectMapper.readValue(is, Player[].class);
                } catch (IOException e) {
                    log.error("Error reading players from input stream for file: {} : {}", fileName, e.getMessage());
                    return players;
                }
                players.addAll(Arrays.asList(playersArray));
            }

        }

        playersMap = new HashMap<>();
        for (Player player : players) {
            playersMap.put(player, player);
        }

        return players;
    }

    private List<Player> loadFromFile() {
        try {
            Player[] players = objectMapper.readValue(new File(saveFileName), Player[].class);
            return Arrays.asList(players);

        } catch (IOException e) {
            log.error("error loading file {}", e.getMessage());

        }

        return null;
    }

    public void setPlayerDrafted(Player player, boolean drafted) {
        playersMap.get(player).setHasBeenDrafted(drafted);
        savePlayersMap();
    }

    private void savePlayersMap() {
        log.info("Saving to : {}", saveFileName);

        try {
            objectMapper.writeValue(new File(saveFileName), playersMap.values());
        } catch (IOException e) {
            log.error("Unable to save file: {}", e.getMessage());
        }

    }

}
