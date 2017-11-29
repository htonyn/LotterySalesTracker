package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.DayResult;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
// ====================================================================== //
// Stats module that will let the user see specific stats per day
// ====================================================================== //
public class Stats extends GridPane implements FileListener {
    // ====================================================================== //
    // Constructor consists of a single filedrag and a listener for the event
    // that the filedrag emits upon the user selecting a file so that the
    // UI will display a TableView of the specific file selected (.json)
    // ====================================================================== //
    public Stats() {
        FileDrag fd = new FileDrag();
        add(fd, 0, 0);
        fd.addListener(this);
    }
    // ====================================================================== //
    // The scan module listens to the file drag and will create a tableview
    // containing the json file opened.
    // Future Update: May make it more flexible in that it would also accept
    // result json files and not just ticket json files. While you can extract
    // instance variables from the json, we would need a new table format to
    // accept the results object (DayResult).
    // ====================================================================== //
    @Override
    public void fileAccepted(String path) {
        System.out.println("Stats: File Accepted");
        ScrollPane statTable;
        if(path.contains("Ticket")) {
            System.out.println("Ticket File");
            List<Ticket> tickets = DatabaseTO.getTickets(path);
            statTable = new TicketTable(FXCollections.observableArrayList(tickets));
        } else {
            System.out.println("Result File");
            List<DayResult> results = DatabaseTO.getResults(path);
            statTable = new ResultTable(FXCollections.observableArrayList(results));
        }
        while(getChildren().size() > 1) {
            getChildren().remove(1);
        }
        add(statTable, 0, 1);
    }
}
