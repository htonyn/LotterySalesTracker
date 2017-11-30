/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker;

import com.mycompany.lotterysalestracker.Model.Ticket;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TicketTable extends ScrollPane {
    private TableView<Ticket> table = new TableView();
    ObservableList<Ticket> tickets;
    public TicketTable(ObservableList<Ticket> ticketList){
        tickets = FXCollections.observableArrayList(ticketList);
        
        TableColumn gameNameColumn = new TableColumn("Game Name");
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        
        TableColumn gameNumberColumn = new TableColumn("Game Number");
        gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        
        TableColumn ticketNumberColumn = new TableColumn("Ticket Number");
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
        
        TableColumn bookNumberColumn = new TableColumn("Book Number");
        bookNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bookNumber"));
        
        table.getColumns().addAll(gameNameColumn, gameNumberColumn, ticketNumberColumn, bookNumberColumn);
        table.setItems(ticketList);
        table.setPrefWidth(700);
        this.setPrefWidth(800);
        setContent(table);
    }
    // ====================================================================== //
    // Returns the selected ticket book in the GameTable TableView
    // Main purpose is used for removing games.
    // ====================================================================== //
    public Ticket getSelectedItem() {
        if(table.getSelectionModel().getSelectedItem() != null) {
            return table.getSelectionModel().getSelectedItem();
        }
        return null;
    }
    // ====================================================================== //
    // Upon adding or removing a ticket book to the book list, this function 
    // will update the TicketTable TableView so changes are seen immediately 
    // without reinitializing the object and readding it to the view.
    // Future Update: In MVC, the tableView will be listening to one central
    // observable list thus removing the need for this.
    // ====================================================================== //
    public void setItems(List<Ticket> ticketList) {
        tickets = FXCollections.observableArrayList(ticketList);
        table.setItems(tickets);
    }
}
