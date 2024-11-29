package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UpdateScoreboard {
    public static void UpdateScore(){
        Gson gson = new Gson();
        Type scoreboardType = new TypeToken<List<GetPlayers>>() {}.getType();
        List<GetPlayers> scoreboard;

        // Load existing scoreboard
        try (FileReader reader = new FileReader("scoreboard.json")) {
            scoreboard = gson.fromJson(reader, scoreboardType);
        } catch (IOException e) {
            scoreboard = new ArrayList<>(); // Create a new scoreboard if the file doesn't exist
        }

        // Add new player
        scoreboard.add(new GetPlayers("Daisy", 180));

        // Save updated scoreboard
        try (FileWriter writer = new FileWriter("scoreboard.json")) {
            gson.toJson(scoreboard, writer);
            System.out.println("Updated scoreboard saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
