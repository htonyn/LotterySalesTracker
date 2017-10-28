/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ponk
 */
public class Game {
    
    
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty gameNumber;

    public Game(String name, int gameNumber) {
        this.name = new SimpleStringProperty(name);
        this.gameNumber = new SimpleIntegerProperty(gameNumber);
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
    
    
}
