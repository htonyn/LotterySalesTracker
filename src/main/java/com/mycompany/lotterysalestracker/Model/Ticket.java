/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker.Model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Ponk
 */
public class Ticket {
    
    private final SimpleIntegerProperty gameNumber;
    private final SimpleIntegerProperty ticketNumber;
    private final SimpleIntegerProperty bookNumber;

    public Ticket(int gameNumber, int ticketNumber, int bookNumber) {
        this.gameNumber = new SimpleIntegerProperty(gameNumber);
        this.ticketNumber = new SimpleIntegerProperty(ticketNumber);
        this.bookNumber = new SimpleIntegerProperty(bookNumber);
    }    

    public int getGameNumber() {
        return gameNumber.get();
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber.set(gameNumber);
    }

    public int getTicketNumber() {
        return ticketNumber.get();
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber.set(ticketNumber);
    }

    public int getBookNumber() {
        return bookNumber.get();
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber.set(bookNumber);
    }
}
