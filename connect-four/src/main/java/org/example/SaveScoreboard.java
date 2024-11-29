package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.LoadScoreboard;

public class SaveScoreboard {
    public List<GetPlayers> scoreboard = new ArrayList<>();

    public void SaveScore(String name,int highScore){
        SaveScoreboard saveScoreboard = new SaveScoreboard();
        LoadBoard(saveScoreboard);
        // Add multiple players to the list
        //saveScoreboard.scoreboard.add(new GetPlayers("Alice", 100));
        boolean updated = false;
        for (GetPlayers player : saveScoreboard.scoreboard) {
            if(name == player.getName().toString());
            {
                player.setHighScore(player.getHighScore()+1);
                updated = true;
            }
        }
        if (!updated)
        {
            saveScoreboard.scoreboard.add(new GetPlayers(name,highScore));
        }

        // Convert the list to JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Save JSON to a file
        try (FileWriter writer = new FileWriter("scoreboard.json")) {
            gson.toJson(saveScoreboard.scoreboard, writer);
            System.out.println("Scoreboard saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadBoard(SaveScoreboard saveScoreboard){
        LoadScoreboard ls = new LoadScoreboard();
        ls.LoadScore();
        saveScoreboard.scoreboard = ls.scoreboard;
    }

}
