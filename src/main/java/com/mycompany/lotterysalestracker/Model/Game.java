package com.mycompany.lotterysalestracker.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Game {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty gameNumber;
    private final SimpleIntegerProperty gameValue;

    public Game(String name, int gameNumber, int gameValue) {
        this.name = new SimpleStringProperty(name);
        this.gameNumber = new SimpleIntegerProperty(gameNumber);
        this.gameValue = new SimpleIntegerProperty(gameValue);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getGameNumber() {
        return gameNumber.get();
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber.set(gameNumber);
    }
    
    public int getGameValue() {
        return gameValue.get();
    }

    public void setGameValue(int gameValue) {
        this.gameValue.set(gameValue);
    }
}
