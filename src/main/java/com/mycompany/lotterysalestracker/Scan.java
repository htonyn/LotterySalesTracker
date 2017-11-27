/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.DayResult;
import com.mycompany.lotterysalestracker.Model.Game;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Scan extends GridPane {
    private String gameName;
    private TextArea scanTicketField;
    ArrayList<Ticket> yesterday;
    ArrayList<Ticket> today;
    ArrayList<DayResult> result;
    List<Game> games;
    public Scan() {
        yesterday = new ArrayList<>();
        today = new ArrayList<>();
        result = new ArrayList<>();
        setPrefWidth(1000);
        VBox instruction = new VBox(1);
        instruction.setPadding(new Insets(5, 5, 5, 5));
        Text title = new Text("Instructions");
        title.setStyle("-fx-underline: true");
        instruction.getChildren().addAll(
            title,
            new Text("1. Scan the Ticket"),
            new Text("2. Ticket scanned will be displayed"),
            new Text("3. Scan next ticket")
        );
        add(instruction, 0, 0);
        GridPane bookBox = new GridPane();
        bookBox.setPadding(new Insets(5, 5, 5, 5));
        add(bookBox, 1, 0);
        setHgrow(bookBox, Priority.ALWAYS);
        setHgrow(instruction, Priority.ALWAYS);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Text gameName = new Text("Scan Game: "+dateFormat.format(date));
        bookBox.add(gameName, 0, 0);
        bookBox.setHalignment(gameName, HPos.CENTER);
        Region r = new Region();
//        bookBox.add(r, 1, 1);
//        bookBox.setHgrow(r, Priority.ALWAYS);
        final String cssDefault = "-fx-border-color: blue;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-border-style: dashed;\n";
        VBox labels = new VBox(10);
        bookBox.setHgrow(labels, Priority.ALWAYS);
        labels.setPrefHeight(800);
//        labels.setStyle(cssDefault);
        bookBox.add(labels, 0, 1);
        Region r1 = new Region();
        Region r2 = new Region();
        Region r3 = new Region();
        Region r4 = new Region();
        Region r6 = new Region();
        HBox gameNameRow = new HBox();
        Label gameNameLabel = new Label("VALUE");
        gameNameRow.getChildren().addAll(new Text("Game Name"), r6, gameNameLabel);
        gameNameRow.setHgrow(r6, Priority.ALWAYS);
        HBox gameIdRow = new HBox();
        Label gameIDLabel = new Label("VALUE");
        gameIdRow.getChildren().addAll(new Text("Game #"), r1, gameIDLabel);
        gameIdRow.setHgrow(r1, Priority.ALWAYS);
        HBox bookNumRow = new HBox();
        Label bookIDLabel = new Label("VALUE");
        bookNumRow.getChildren().addAll(new Text("Book #"), r2, bookIDLabel);
        bookNumRow.setHgrow(r2, Priority.ALWAYS);
        HBox numSoldRow = new HBox();
        Label numSoldLabel = new Label("VALUE");
        numSoldRow.getChildren().addAll(new Text("# Sold"), r3, numSoldLabel);
        numSoldRow.setHgrow(r3, Priority.ALWAYS);
        HBox totalValueRow = new HBox();
        Label totalValueLabel = new Label("VALUE");
        totalValueRow.getChildren().addAll(new Text("Total"), r4, totalValueLabel);
        totalValueRow.setHgrow(r4, Priority.ALWAYS);
        
        Label scanTicketLabel = new Label("Input");
        scanTicketLabel.setPrefWidth(100);
        scanTicketField = new TextArea();
        scanTicketField.setPrefHeight(25);
//        scanTicketField.setMaxHeight(25);
        scanTicketField.setPrefWidth(200);
        HBox inputRow = new HBox(4);
        Region r5 = new Region();
        inputRow.getChildren().addAll(scanTicketLabel, r5, scanTicketField);
        inputRow.setHgrow(r5, Priority.ALWAYS);
        // No button b/c scan for new line character then parse the data
        scanTicketField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                if(newValue.length() == 25 && newValue.substring(newValue.length()-1).equals("\n")) {
                    System.out.println("Input: "+newValue);
                    System.out.println("===================================");
                    int gameID = Integer.parseInt(newValue.substring(0,4));
                    System.out.println("Game ID: " +gameID);
                    int bookID = Integer.parseInt(newValue.substring(4,11));
                    System.out.println("Book ID: " +bookID);
                    int ticketID = Integer.parseInt(newValue.substring(11,14));
                    System.out.println("Ticket ID: " +ticketID);
                    System.out.println("===================================");
                    gameIDLabel.setText(gameID+"");
                    bookIDLabel.setText(bookID+"");
                    int numSold = 0;
                    int price = 0;
                    for (Ticket ticket : yesterday) {
                        if (ticket.getGameNumber() == gameID) {
                            numSold = ticketID - ticket.getTicketNumber();
                            for (Game game : games) {
                                if (game.getGameNumber() == gameID) {
                                    gameNameLabel.setText(game.getName());
                                    price = game.getGameValue() * numSold;
                                    int duplicate = 0;
                                    for (Ticket ticketDupe : today) {
                                        System.out.println("Ticket: "+ticketDupe.getBookNumber()+" vs Scan: "+bookID);
                                        if(ticketDupe.getBookNumber() == bookID) {
                                            duplicate = 1;
                                            break;
                                        }
                                    }
                                    System.out.println(duplicate);
                                    if (duplicate == 0) {
                                        today.add(new Ticket(getTicketName(gameID), gameID, bookID, ticketID));
                                        result.add(new DayResult(getTicketName(gameID), gameID, getTicketValue(gameID), numSold, price));
                                    }                                    
                                    break;
                                }
                            }
                        }
                    }
                    numSoldLabel.setText(numSold+" Tickets");
                    // Pull book size
                    totalValueLabel.setText("$"+price);
                    Platform.runLater(() -> {
                       scanTicketField.clear(); 
                    });
                } else if ((newValue.length() > 25 || (((newValue.length() < 25) && (newValue.length() > 0)) && newValue.substring(newValue.length()-1).equals("\n")))) {
                    Platform.runLater(() -> {
                       scanTicketField.clear(); 
                    }); 
                }
            }
        );
        
        Button finishDay = new Button("Finish Day");
        finishDay.setOnAction(
            (event) -> {
                DatabaseTO.createDailyReport(today);
                TableView<DayResult> table = new TableView();
                TableColumn gameNameColumn = new TableColumn("Name");
                gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
                TableColumn gameNumberColumn = new TableColumn("Game Number");
                gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
                TableColumn ticketValueColumn = new TableColumn("Ticket Value");
                ticketValueColumn.setCellValueFactory(new PropertyValueFactory<>("ticketValue"));
                TableColumn ticketSoldColumn = new TableColumn("Tickets Sold");
                ticketSoldColumn.setCellValueFactory(new PropertyValueFactory<>("ticketSold"));
                TableColumn totalValueColumn = new TableColumn("Total Value");
                totalValueColumn.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
                table.getColumns().addAll(gameNameColumn, gameNumberColumn, ticketValueColumn, ticketSoldColumn, totalValueColumn);
                table.setItems(FXCollections.observableArrayList(result));
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.getDialogPane().setMaxHeight(USE_PREF_SIZE);
                alert.setTitle("Day Finished!");
                alert.setHeaderText("Results for the Date: "+dateFormat.format(date));
                ScrollPane scroll = new ScrollPane();
                scroll.setMaxHeight(600);
                scroll.setContent(table);
                alert.getDialogPane().setContent(scroll);
                alert.showAndWait();
            }
        );
                
        labels.getChildren().addAll(
            gameNameRow, gameIdRow, bookNumRow, numSoldRow, totalValueRow, inputRow, finishDay
        );
        loadGames();
        getYesterdayJSON();
    }
    protected void clearBox() {
        if (scanTicketField != null) {
            scanTicketField.clear();
        }
    }
    public void getYesterdayJSON() {
        for(Ticket ticket : DatabaseTO.getTickets("Tickets"+getYesterdayDateString()+".json")) {
            yesterday.add(ticket);
        }
    }

    public String getTicketName(int gameid) {
        for (Game game : games) {
            if (gameid == game.getGameNumber()) {
                return game.getName();
            }
        }
        return null;
    }
    
    public int getTicketValue(int gameid) {
        for (Game game : games) {
            if (gameid == game.getGameNumber()) {
                return game.getGameValue();
            }
        }
        return -1;
    }
    
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }
    public void loadGames() {
        this.games = DatabaseTO.getGames();
//        for (Game game : games) {
//            System.out.println(game.getName());
//        }
    }
    
    // The methods below are merely dummy data for testing each part before
    // real implementation
    public void readJSON() {
        for(Ticket ticket : DatabaseTO.getTickets("Tickets2017-11-24.json")){
            System.out.println("Name: " + getTicketName(ticket.getGameNumber()));
            System.out.println("Number: " + ticket.getGameNumber());
            System.out.println("Ticket Number: " + ticket.getGameNumber());
        }
    }
    public void generateGames() {
        List<Game> games = new ArrayList<>();
        games.add(new Game("Triple Play", 1282, 2));
        games.add(new Game("Jumbo Bucks Classic", 1002, 2));
        games.add(new Game("Bass Pro Shops", 1283, 3));
        games.add(new Game("Hit $500", 1120, 5));
        games.add(new Game("Extra Play", 1284, 5));
        games.add(new Game("50X The Money", 1011, 10));
        
        DatabaseTO.updateGameList(games);
    }
    public void generateDummyJSON() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(getTicketName(1282), 1282, 24669, 12));
        tickets.add(new Ticket(getTicketName(1283), 1283, 6258, 22));
        tickets.add(new Ticket(getTicketName(1120), 1120, 416361, 54));
        tickets.add(new Ticket(getTicketName(1284), 1284, 9200, 2));
        tickets.add(new Ticket(getTicketName(1011), 1011, 1614936, 21));
        tickets.add(new Ticket(getTicketName(1002), 1002, 1320339, 41));
        
        DatabaseTO.createDailyReport(tickets);
    }
    public void そうですね() {
        System.out.println("Test");
    }
}
