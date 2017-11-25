package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

import javafx.scene.control.TableView;

public class Stats extends GridPane implements FileListener {
    
    public Stats() {
        FileDrag fd = new FileDrag();
        add(fd, 0, 0);
        fd.addListener(this);
        List<Ticket> tickets = DatabaseTO.getTickets("Tickets2017-11-25.json");
        TicketTable tt = new TicketTable(FXCollections.observableArrayList(tickets));
        add(tt, 0, 2);
    }

    @Override
    public void fileAccepted(String path) {
        System.out.println("Stats: File Accepted");
        List<Ticket> tickets = DatabaseTO.getTickets(path);
        TicketTable tt = new TicketTable(FXCollections.observableArrayList(tickets));
        add(tt, 0, 1);
    }
}
