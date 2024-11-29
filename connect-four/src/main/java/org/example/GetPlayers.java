package org.example;

public class GetPlayers {

    private String name;
    private int highScore;

    // Constructors
    public GetPlayers(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

}
