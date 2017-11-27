package com.mycompany.lotterysalestracker.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DayResult {
    private final SimpleStringProperty gameName;
    private final SimpleIntegerProperty gameNumber;
    private final SimpleIntegerProperty ticketValue;
    private final SimpleIntegerProperty ticketSold;
    private final SimpleIntegerProperty totalValue;

    public DayResult (String gameName, int gameNumber, int ticketValue, int ticketSold, int totalValue) {
        this.gameName = new SimpleStringProperty(gameName);
        this.gameNumber = new SimpleIntegerProperty(gameNumber);
        this.ticketValue = new SimpleIntegerProperty(ticketValue);
        this.ticketSold = new SimpleIntegerProperty(ticketSold);
        this.totalValue = new SimpleIntegerProperty(totalValue);
        
    }    
    
    public String getGameName(){
        return gameName.get();
    }
    public int getTicketValue(){
        return ticketValue.get();
    }    
    public void setGameName(String newGameName){
        this.gameName.set(newGameName);
    }
    
    public int getGameNumber() {
        return gameNumber.get();
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber.set(gameNumber);
    }

    public int getTicketSold() {
        return ticketSold.get();
    }

    public int getTotalValue() {
        return totalValue.get();
    }
}
