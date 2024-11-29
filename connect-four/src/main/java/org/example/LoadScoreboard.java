package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class LoadScoreboard {

    public List<GetPlayers> scoreboard;
    public void LoadScore(){
        Gson gson = new Gson();

        // Define the type for deserialization
        Type scoreboardType = new TypeToken<List<GetPlayers>>() {}.getType();

        // Read JSON from the file and convert it back into a list
        try (FileReader reader = new FileReader("scoreboard.json")) {
            scoreboard = gson.fromJson(reader, scoreboardType);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteScoreboard(){
        for (GetPlayers player : scoreboard) {
            System.out.println("Name: " + player.getName() + ", High Score: " + player.getHighScore());
        }
        System.out.println();
    }

}
