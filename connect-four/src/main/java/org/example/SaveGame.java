package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame {
    public void saveGameState(int[][] gameMap){
        String filePath = "connect4_saved_state.txt";

        // Write the array to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : gameMap) {
                for (int value : row) {
                    writer.write(value + " ");
                }
                writer.newLine(); // New line for each row
            }
            System.out.println("Game is saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
