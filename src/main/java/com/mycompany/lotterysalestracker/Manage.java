package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.DayResult;
import com.mycompany.lotterysalestracker.Model.Game;
import com.mycompany.lotterysalestracker.Model.Ticket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Manage extends GridPane {
    GridPane addGameBox;
    GridPane currentGameBox;
    List<Game> games;
    static List<Ticket> activeBooks;
    GameTable gt;
    TicketTable tt;
    public Manage() {        
        initializeView();
        initializeData();
    }
    // ====================================================================== //
    // 
    // ====================================================================== //
    private void initializeView() {
        addGameBox = new GridPane();
        currentGameBox = new GridPane();
        GridPane addGameControls = new GridPane();
        Button addGame = new Button("Add Game");
        Button removeGame = new Button("Remove Game");
        addGameBox.add(new Text("Game List"), 0, 0);
        Region vSpace = new Region();
        addGameBox.add(vSpace, 0, 2);
        addGameBox.setVgrow(vSpace, Priority.ALWAYS);
        addGameBox.setHgrow(vSpace, Priority.ALWAYS);
        addGameBox.add(addGameControls, 0, 3);
        addGameControls.add(addGame, 0, 0);
        Region r = new Region();
        addGameControls.add(r, 1, 0);
        addGameControls.setHgrow(r, Priority.ALWAYS);
        addGameControls.add(removeGame, 2, 0);        
        setHgrow(addGameBox, Priority.ALWAYS);
        addGame.setOnAction(
            (event) -> {
                addGameDialog();
            }
        );
        removeGame.setOnAction(
            (event) -> {
                if (gt.getSelectedItem() != null) {
                    Game selectedGame = gt.getSelectedItem();
                    // Removes the game from the list
                    for(Iterator<Game> iterator = games.iterator(); iterator.hasNext(); ) {
                        if(iterator.next().getGameNumber() == selectedGame.getGameNumber()) {
                            iterator.remove();
                            DatabaseTO.updateGameList(games);
                            gt.setItems(games);
                            break;
                        }
                    }
                    // Because the game is removed, all the books that are
                    // connected to the game will also be removed
                    for(Iterator<Ticket> iterator = activeBooks.iterator(); iterator.hasNext(); ) {
                        if (iterator.next().getGameNumber() == selectedGame.getGameNumber()) {
                            iterator.remove();
                            tt.setItems(activeBooks);
                        }
                    }
                }
            }
        );
        GridPane currentGameControls = new GridPane();
        Region vSpace1 = new Region();
        Button addBook = new Button("Add Book");
        Button removeBook = new Button("Remove Book");
        Button finishBook = new Button("Finish Book");
        Region r1 = new Region();
        Region r2 = new Region();
        currentGameControls.add(addBook, 0, 0);
        currentGameControls.add(r1, 1, 0);
        currentGameControls.add(removeBook, 2, 0);
        currentGameControls.add(r2, 3, 0);
        currentGameControls.add(finishBook, 4, 0);
        currentGameControls.setHgrow(r1, Priority.ALWAYS);
        currentGameControls.setHgrow(r2, Priority.ALWAYS);
        currentGameBox.add(new Text("Current Books"), 0, 0);
        currentGameBox.add(currentGameControls, 0, 3);
        currentGameBox.add(vSpace1, 0, 2);
        currentGameBox.setVgrow(vSpace1, Priority.ALWAYS);
        currentGameBox.setHgrow(currentGameControls, Priority.ALWAYS);
        
        addBook.setOnAction( 
            (event) -> {
                addBook();
            }
        );
        removeBook.setOnAction( 
            (event) -> {
                if (tt.getSelectedItem() != null) {
                    Ticket selectedTicket = tt.getSelectedItem();
                    for(Iterator<Ticket> iterator = activeBooks.iterator(); iterator.hasNext(); ) {
                        if(iterator.next().getGameNumber() == selectedTicket.getGameNumber()) {
                            iterator.remove();
                            tt.setItems(activeBooks);
                            break;
                        }
                    }
                }
            }
        );
        finishBook.setOnAction(
            (event) -> {
                if (tt.getSelectedItem() != null) {
                    // If a ticket is finished, results need to calculate
                    // the rest of the prices
                    // Then if a ticket is finished, it would not be added
                    // to the new list of tickets
                    Ticket selectedTicket = tt.getSelectedItem();
                    List<DayResult> results = Scan.getResultList();
                    
                    for(Iterator<Ticket> iterator = activeBooks.iterator(); iterator.hasNext(); ) {
                        Ticket currentTicket = iterator.next();
                        if((currentTicket.getGameNumber() == selectedTicket.getGameNumber()) && (currentTicket.getBookNumber() == selectedTicket.getBookNumber())) {
                            for (Game game : games) {
                                if (currentTicket.getGameNumber() == game.getGameNumber()) {
                                    int numSold = (int) (300/game.getGameValue()) - currentTicket.getTicketNumber();
                                    int totalValue = numSold * game.getGameValue();
                                    results.add(new DayResult(game.getName(), game.getGameNumber(), game.getGameValue(), numSold, totalValue));
                                }
                            }
                            iterator.remove();
                            tt.setItems(activeBooks);
                            break;
                        }
                    }
                } else {
                    System.out.println("No book selected");
                }
            }
        );
        
        TabPane tp = new TabPane();
        tp.setStyle("-fx-background-color: white;");
        tp.setPrefWidth(9999);
        tp.setPrefHeight(9999);
        
        Tab gamePage = new Tab("All Games");
        gamePage.setContent(addGameBox);
        gamePage.setOnCloseRequest(e -> e.consume());
        Tab currentPage = new Tab("Current Books");
        currentPage.setContent(currentGameBox);
        currentPage.setOnCloseRequest(e -> e.consume());
        
        add(tp, 0, 0, 3, 1);
        setHgrow(tp, Priority.ALWAYS);
        tp.getTabs().addAll(gamePage, currentPage);
    }
    // ====================================================================== //
    // 
    // ====================================================================== //
    private void initializeData() {
        games = DatabaseTO.getGames();
        gt = new GameTable();
        activeBooks = DatabaseTO.getTickets("Tickets"+getYesterdayDateString()+".json");
        tt = new TicketTable(FXCollections.observableArrayList(activeBooks));
        
        addGameBox.add(gt, 0, 1, 3, 1);
        currentGameBox.add(tt, 0, 1, 3, 1);
    }
    
    // ====================================================================== //
    // 
    // ====================================================================== //
    public void addGameDialog() {
        Dialog addGameDialog = new Dialog();
        addGameDialog.setTitle("Add Game");

        GridPane addGameLayout = new GridPane();
        addGameLayout.setHgap(10);
        addGameLayout.setVgap(10);
        addGameLayout.setPadding(new Insets(20, 150, 10, 10));

        TextField addGameName = new TextField();
        addGameLayout.add(addGameName, 0, 1);
        NumericTextField addGameNumber = new NumericTextField();
        addGameLayout.add(addGameNumber, 1, 1);
        NumericTextField addGameValue = new NumericTextField();
        addGameLayout.add(addGameValue, 2, 1);
        addGameLayout.add(new Text("Game Name"), 0, 0);
        addGameLayout.add(new Text("Game Number"), 1, 0);
        addGameLayout.add(new Text("Game Value"), 2, 0);

        ButtonType submitGameDialog = new ButtonType("Add Game", ButtonData.OK_DONE);
        addGameDialog.getDialogPane().getButtonTypes().addAll(submitGameDialog, ButtonType.CANCEL);

        addGameDialog.setResultConverter(submitGame -> {
            if (submitGame == submitGameDialog) {
                System.out.println("Add Game Dialog: Button Pressed");
                if ((addGameName.getText() == null) || (addGameNumber.getText() == null) || (addGameValue.getText() == null)) {
                    System.out.println("Add Game Dialog: Invalid input");
                    return null;
                } else {       
                    return new Game(addGameName.getText(), Integer.parseInt(addGameNumber.getText()), Integer.parseInt(addGameValue.getText()));
                }
            }                    
            return null;
        });

        addGameDialog.getDialogPane().setContent(addGameLayout);
        Optional<Game> newGame = addGameDialog.showAndWait();
        newGame.ifPresent(gameData -> {
            games.add(new Game(gameData.getName(), gameData.getGameNumber(), gameData.getGameValue()));
            DatabaseTO.updateGameList(games);
            gt.setItems(games);
            System.out.println(addGameBox.getChildren().indexOf(gt));
        });
    }
    public void addBook() {
        Dialog addBookDialog = new Dialog();
        addBookDialog.setTitle("Add Book");
        
        GridPane addBookLayout = new GridPane();
        addBookLayout.setPrefWidth(600);
        addBookLayout.setHgap(10);
        addBookLayout.setVgap(10);
        //addBookLayout.setPadding(new Insets(20, 150, 10, 10));
        

        TextArea addBookInput = new TextArea();
        addBookInput.setPrefHeight(25);
        addBookInput.setPrefWidth(600);
        addBookLayout.add(new Text("Scan Here: "), 0, 0);
        addBookLayout.add(addBookInput, 1, 0, 7, 1);

        ButtonType submitBookDialog = new ButtonType("Add Book", ButtonData.OK_DONE);
        addBookDialog.getDialogPane().getButtonTypes().addAll(submitBookDialog, ButtonType.CANCEL);
        
        Label gameNameLabel = new Label("Game Name: ");
        addBookLayout.add(gameNameLabel, 0, 2);
        Label gameNumberLabel = new Label("Game Number: ");
        addBookLayout.add(gameNumberLabel, 3, 2);
        Label bookNumberLabel = new Label("Book: ");
        addBookLayout.add(bookNumberLabel, 6, 2);
        Label ticketNumberLabel = new Label("Ticket: ");
        addBookLayout.add(ticketNumberLabel, 9, 2);
        
        Label gameName = new Label("<Game Name Placeholder>");
        addBookLayout.add(gameName, 1, 2);
        Label gameNumber = new Label("XXXX");
        addBookLayout.add(gameNumber, 4, 2);
        Label bookNumber = new Label("XXXXXXX");
        addBookLayout.add(bookNumber, 7, 2);
        Label ticketNumber = new Label("XXX");
        addBookLayout.add(ticketNumber, 10, 2);
        
        Region r1 = new Region();
        Region r2 = new Region();
        Region r3 = new Region();
        addBookLayout.add(r1, 2, 2);
        addBookLayout.add(r2, 5, 2);
        addBookLayout.add(r3, 8, 2);
        addBookLayout.setHgrow(r1, Priority.ALWAYS);
        addBookLayout.setHgrow(r2, Priority.ALWAYS);
        addBookLayout.setHgrow(r3, Priority.ALWAYS);
        
        addBookInput.textProperty().addListener(
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
                    String gameInputName = "";
                    System.out.println("===================================");
                    for (Game game : games) {
                        if (game.getGameNumber() == gameID) {
                            gameInputName = game.getName();
                        } else {
                            gameInputName = "Game not found";
//                            addGameDialog();
                        }
                    }
                    gameName.setText(gameInputName);
                    gameNumber.setText(gameID+"");
                    bookNumber.setText(bookID+"");
                    ticketNumber.setText(ticketID+"");
                } else if ((newValue.length() > 25 || (((newValue.length() < 25) && (newValue.length() > 0)) && newValue.substring(newValue.length()-1).equals("\n")))) {
                    Platform.runLater(() -> {
                       addBookInput.clear(); 
                    }); 
                }
            }
        );
        addBookDialog.setResultConverter(submitBook -> {
            if (submitBook == submitBookDialog) {
                System.out.println("Add Book Dialog: Button Pressed");
                if ((gameName.getText() == null) || (gameNumber.getText() == null) || (bookNumber.getText() == null) || (ticketNumber.getText() == null)) {
                    System.out.println("Add Game Dialog: Invalid input");
                    return null;
                } else {       
                    return new Ticket(gameName.getText(), Integer.parseInt(gameNumber.getText()), Integer.parseInt(bookNumber.getText()), Integer.parseInt(ticketNumber.getText()));
                }
            }                    
            return null;
        });

        addBookDialog.getDialogPane().setContent(addBookLayout);
        Optional<Ticket> newBook = addBookDialog.showAndWait();
        newBook.ifPresent(bookData -> {
            activeBooks.add(new Ticket(bookData.getGameName(), bookData.getGameNumber(), bookData.getBookNumber(), bookData.getTicketNumber()));
            tt.setItems(activeBooks);
            System.out.println(addGameBox.getChildren().indexOf(gt));
        });
    }
    // ====================================================================== //
    // 
    // 
    // ====================================================================== //
    public static List<Ticket> getActiveBookList() {
        return activeBooks;
    }
    public static void updateActiveBookList(List<Ticket> updatedBook) {
        activeBooks = updatedBook;
    }
    // ====================================================================== //
    // Function calls used to generate a date format used to generating unique
    // Future Update: Add this into a Util class so that it may be re-used more
    // efficiently
    // ====================================================================== //
    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }
}
