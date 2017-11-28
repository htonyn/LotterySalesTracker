package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.layout.GridPane;

public class Stats extends GridPane implements FileListener {
    public Stats() {
        FileDrag fd = new FileDrag();
        add(fd, 0, 0);
        fd.addListener(this);
    }

    @Override
    public void fileAccepted(String path) {
        System.out.println("Stats: File Accepted");
        List<Ticket> tickets = DatabaseTO.getTickets(path);
        System.out.println(getChildren().size());
        while(getChildren().size() > 1) {
            getChildren().remove(1);
        }
        TicketTable tt = new TicketTable(FXCollections.observableArrayList(tickets));
        add(tt, 0, 1);
    }
}
