/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.Game;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TicketTable extends ScrollPane {
    private TableView<Ticket> table = new TableView();
    public TicketTable(ObservableList<Ticket> gameList){
        
        TableColumn gameNameColumn = new TableColumn("Game Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        
        TableColumn gameNumberColumn = new TableColumn("Game Number");
        gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        
        TableColumn ticketNumberColumn = new TableColumn("Ticket Number");
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
        
        TableColumn bookNumberColumn = new TableColumn("Book Number");
        bookNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bookNumber"));
        
        table.getColumns().addAll(gameNameColumn, gameNumberColumn, ticketNumberColumn, bookNumberColumn);
        table.setItems(gameList);
        table.setPrefWidth(700);
        this.setPrefWidth(800);
        setContent(table);
    }
    
}
