/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker;

import com.mycompany.lotterysalestracker.Model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Ponk
 */
public class TicketTable extends ScrollPane{
    
    private final TableView<Ticket> table = new TableView<>();
    
    public TicketTable(ObservableList<Ticket> gameList){
        
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn gameNumberColumn = new TableColumn("Game Number");
        gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        
        TableColumn ticketNumberColumn = new TableColumn("Ticket Number");
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
        
        TableColumn bookNumberColumn = new TableColumn("Book Number");
        bookNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bookNumber"));
        
        table.getColumns().addAll(nameColumn, gameNumberColumn);
        table.setItems(gameList);
        this.getChildren().addAll(table);
    }
}
