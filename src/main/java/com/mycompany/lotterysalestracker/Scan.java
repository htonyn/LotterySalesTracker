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
import java.util.Iterator;
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
    private TextArea scanTicketField;
    List<Ticket> yesterday;
    List<Ticket> today;
    static List<DayResult> result;
    List<Game> games;
    
    public Scan() {
        yesterday = new ArrayList<>();
        today = new ArrayList<>();
        result = new ArrayList<>();
        setPrefWidth(1000);
        
        VBox instruction = buildInstruction();
        add(instruction, 0, 0);
        setHgrow(instruction, Priority.ALWAYS);
        
        GridPane bookBox = new GridPane();
        bookBox.setPadding(new Insets(5, 5, 5, 5));
        add(bookBox, 1, 0);
        setHgrow(bookBox, Priority.ALWAYS);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Text gameName = new Text("Scan Game: "+dateFormat.format(date));
        bookBox.add(gameName, 0, 0);
        bookBox.setHalignment(gameName, HPos.CENTER);

        VBox labels = new VBox(10);
        bookBox.setHgrow(labels, Priority.ALWAYS);
        labels.setPrefHeight(800);
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
//        scanTicketField.requestFocus();
        scanTicketField.setPrefHeight(25);
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
                    // This gets the current active books so that the program
                    // can iterate through them to find if the recently scanned
                    // ticket is continued.
                    
                    List<Ticket> activeBooks = Manage.getActiveBookList();
                    for (Ticket ticket : activeBooks) {
                        if ((ticket.getGameNumber() == gameID) && (ticket.getBookNumber() == bookID)) {
                            numSold = ticketID - ticket.getTicketNumber();
                            for (Game game : games) {
                                if (game.getGameNumber() == gameID) {
                                    gameNameLabel.setText(game.getName());
                                    price = game.getGameValue() * numSold;
                                    numSoldLabel.setText(numSold+" Tickets");
                                    totalValueLabel.setText("$"+price);
                                    int duplicate = 0;
                                    for (Ticket ticketDupe : today) {
                                        System.out.println("Ticket: "+ticketDupe.getBookNumber()+" vs Scan: "+bookID);
                                        if(ticketDupe.getBookNumber() == bookID) {
                                            duplicate = 1;
                                            break;
                                        }
                                    }
//                                    System.out.println(duplicate);
                                    if (duplicate == 0) {
                                        for(Iterator<Ticket> iterator = activeBooks.iterator(); iterator.hasNext(); ) {
                                            Ticket currentBook = iterator.next();
                                            if ((currentBook.getGameNumber() == ticket.getGameNumber()) && (currentBook.getBookNumber() == ticket.getBookNumber())) {
                                                System.out.println("Scan: Removing scanned ticket from activebook");
                                                iterator.remove();
                                                break;
                                            }
                                        }
                                        Manage.updateActiveBookList(activeBooks);
                                        today.add(new Ticket(getTicketName(gameID), gameID, bookID, ticketID));
                                        result.add(new DayResult(getTicketName(gameID), gameID, getTicketValue(gameID), numSold, price));
                                    }
                                    Platform.runLater(() -> {
                                        scanTicketField.clear(); 
                                     });
                                    break;
                                }
                            }
                        }
                    }
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
                List<Ticket> activeBooks = Manage.getActiveBookList();
                for (Ticket ticket : activeBooks) {
                    result.add(new DayResult(ticket.getGameName(), ticket.getGameNumber(), getTicketValue(ticket.getGameNumber()), 0, 0));
                    today.add(ticket);
                }
                System.out.println(today);
                DatabaseTO.createDailyReport(today, result);
                ResultTable resultTable = new ResultTable(FXCollections.observableArrayList(result));
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.getDialogPane().setMaxHeight(USE_PREF_SIZE);
                alert.setTitle("Day Finished!");
                alert.setHeaderText("Results for the Date: "+dateFormat.format(date));
                ScrollPane scroll = new ScrollPane();
                scroll.setMaxHeight(600);
                scroll.setContent(resultTable);
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
    
    private VBox buildInstruction() {
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
        return instruction;
    }
    
    
    
    public static List<DayResult> getResultList() {
        return result;
    }
    public static void updateResultList(List<DayResult> newResult) {
        result = newResult;
    }
    
    private void clearBox() {
        if (scanTicketField != null) {
            scanTicketField.clear();
        }
    }
    private void getYesterdayJSON() {
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
        
        DatabaseTO.createDailyReport(tickets, result);
    }
    public void そうですね() {
        System.out.println("Test");
    }
}
