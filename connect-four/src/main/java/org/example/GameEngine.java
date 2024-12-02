package org.example;

import org.antlr.v4.runtime.misc.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class GameEngine extends LoadScoreboard{

    private static final int ROWS = 6;
    private static final int COLS = 7;
    public int[][] gameMap = new int[6][7];
    public boolean loadChoice = false;
    public String name;
    public String loadGameChoice;

    public static void StartGame()
    {
        LoadScoreboard ls = new LoadScoreboard();
        GameEngine gameEngine = new GameEngine();
        gameEngine.UIController(ls);

        if(gameEngine.loadGameChoice.equals("y"))
        {
            gameEngine.loadChoice = true;
        }

        if (!gameEngine.loadChoice){
            gameEngine.CreateMap();
        }
        else {
            LoadSaved(gameEngine);
        }
        gameEngine.GameManager();
    }

    public void UIController(LoadScoreboard ls){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\t   SCOREBOARD \n");
        ls.LoadScore();
        ls.WriteScoreboard();

        System.out.println("Whats you name: ");
        name = scanner.nextLine();

        System.out.println("Do you want to load the saved state? (y/n)");
        loadGameChoice = scanner.nextLine();

    }

    public void GameManager()
    {
        boolean checker = false;
        boolean win = false;

        //handles game and win check
        do {
            if(PlayerMove()) //miss input
            {
                do {
                    PlayerMove();
                }while(!PlayerMove());
            };
            updateMap();
            checker = Checker();
            if(checker)
            {
                System.out.println("Player 1 has won the game.");
                win = true;
            }
            if(!checker)
            {
                AImove();
                updateMap();
                checker = Checker();
            } else if (!win) {
                System.out.println("Player 2 has won the game.");
            }
        }while(!checker || !win);

        SaveScoreboard saveScoreboard = new SaveScoreboard();
        saveScoreboard.SaveScore(name,1);

    }

    public static void LoadSaved(GameEngine gameEngine) {
        if (gameEngine.loadFromFile("connect4_saved_state.txt")) {
            System.out.println("Game loaded successfully!");
            int i = 1;
            for (int[] row : gameEngine.gameMap) {
                System.out.println();
                System.out.print(i + "  ");
                for (int cell : row) {

                    System.out.print(cell + " ");
                }
                i++;
            }
            System.out.println();
            System.out.println("   a b c d e f g");
        } else {
            System.err.println("Failed to load the game.");
        }
    }

    public boolean loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<int[]> tempGrid = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length != COLS) {
                    System.err.println("Invalid file format: Incorrect number of columns.");
                    return false;
                }
                int[] row = new int[COLS];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                tempGrid.add(row);
            }

            if (tempGrid.size() != ROWS) {
                System.err.println("Invalid file format: Incorrect number of rows.");
                return false;
            }

            // Convert List<int[]> to int[][]
            for (int i = 0; i < ROWS; i++) {
                gameMap[i] = tempGrid.get(i);
            }
            return true;

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading file: " + e.getMessage());
            return false;
        }
    }

    public void CreateMap()
    {
        //map is 6 height x 7 wide
        // y is counted 1-6 x is counted a-g
        for (int i = 0; i < gameMap.length; i++) { // Loop over rows
            for (int j = 0; j < gameMap[i].length; j++) { // Loop over columns
                gameMap[i][j] = 0;
            }
        }

        int i = 1;
        for (int[] row : gameMap) {
            System.out.println();
            System.out.print(i + "  ");
            for (int cell : row) {

                System.out.print(cell + " ");
            }
            i++;
        }
        System.out.println();
        System.out.println("   a b c d e f g");

    }

    public void updateMap()
    {
        int i = 1;
        for (int[] row : gameMap) {
            System.out.println();
            System.out.print(i + "  ");
            for (int cell : row) {

                System.out.print(cell + " ");
            }
            i++;
        }
        System.out.println();
        System.out.println("   a b c d e f g");
    }

    public Boolean PlayerMove()
    {
        int moveCol = -1;

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Make a move, or save the game (S): ");
        String move = scanner2.nextLine();

        //update Map by move
        //convert string to indexes
        switch (move){
            case "a":
                moveCol = 0;
                break;
            case "b":
                moveCol = 1;
                break;
            case "c":
                moveCol = 2;
                break;
            case "d":
                moveCol = 3;
                break;
            case "e":
                moveCol = 4;
                break;
            case "f":
                moveCol = 5;
                break;
            case "g":
                moveCol = 6;
                break;
            case "S":
                SaveGame saveGame = new SaveGame();
                saveGame.saveGameState(gameMap);
                PlayerMove();
                break;

            default:
                System.out.println("Invalid input. Please try again.");
                break;
        }

        if (moveCol == -1)
            return Boolean.TRUE;
        //make move
        for (int i = 5; i > 0; i--) {
            if(gameMap[i][moveCol] == 0){
                gameMap[i][moveCol] = 1;
                break;
            }
        }

        return Boolean.FALSE;
    }

    public Boolean AImove()
    {
        int moveCol = -1;

        Random r = new Random();
        moveCol = r.nextInt(0,6);

        for (int i = 5; i > 0; i--) {
            if(gameMap[i][moveCol] == 0){
                gameMap[i][moveCol] = 2;
                break;
            }
        }
        return false;
    }

    public Boolean Checker()
    {
        //win condition: függöleges, átlós, horizontális
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int player = gameMap[row][col];
                if (player != 0) { // Skip empty cells
                    if (checkHorizontal(gameMap, row, col, player) ||
                            checkVertical(gameMap, row, col, player) ||
                            checkDiagonal(gameMap, row, col, player)) {
                        return true; // Winner found
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(int[][] gameMap, int row, int col, int player) {
        if (col + 3 < COLS) { // Ensure there are enough columns to check
            return gameMap[row][col] == player &&
                    gameMap[row][col + 1] == player &&
                    gameMap[row][col + 2] == player &&
                    gameMap[row][col + 3] == player;
        }
        return false;
    }

    private boolean checkVertical(int[][] gameMap, int row, int col, int player) {
        if (row + 3 < ROWS) { // Ensure there are enough rows to check
            return gameMap[row][col] == player &&
                    gameMap[row + 1][col] == player &&
                    gameMap[row + 2][col] == player &&
                    gameMap[row + 3][col] == player;
        }
        return false;
    }

    private boolean checkDiagonal(int[][] gameMap, int row, int col, int player) {
        // Check diagonal (top-left to bottom-right)
        if (row + 3 < ROWS && col + 3 < COLS) {
            if (gameMap[row][col] == player &&
                    gameMap[row + 1][col + 1] == player &&
                    gameMap[row + 2][col + 2] == player &&
                    gameMap[row + 3][col + 3] == player) {
                return true;
            }
        }
        // Check diagonal (top-right to bottom-left)
        if (row + 3 < ROWS && col - 3 >= 0) {
            if (gameMap[row][col] == player &&
                    gameMap[row + 1][col - 1] == player &&
                    gameMap[row + 2][col - 2] == player &&
                    gameMap[row + 3][col - 3] == player) {
                return true;
            }
        }
        return false;
    }


}
