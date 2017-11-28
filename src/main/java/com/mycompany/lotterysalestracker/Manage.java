package com.mycompany.lotterysalestracker;

import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.Game;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Manage extends GridPane {
    GridPane addGameBox;
    GridPane currentGameBox;
    List<Game> games;
    GameTable gt;
    public Manage() {
        games = DatabaseTO.getGames();
        addGameBox = new GridPane();
        addGameBox.setPrefHeight(800);
        currentGameBox = new GridPane();
        initializeView();
        gt = new GameTable();
        addGameBox.add(gt, 0, 1, 3, 1);
        
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
    
    public void initializeView() {
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
//                System.out.println("Add Game");
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
        );
        removeGame.setOnAction(
            (event) -> {
                System.out.println("Remove Game");
                if (gt.getSelectedItem() != null) {
                    Game selectedGame = gt.getSelectedItem();
                    for(Iterator<Game> iterator = games.iterator(); iterator.hasNext(); ) {
                        if(iterator.next().getGameNumber() == selectedGame.getGameNumber()) {
                            iterator.remove();
                            DatabaseTO.updateGameList(games);
                            gt.setItems(games);
                            break;
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
                System.out.println("Manage: Add Book");
            }
        );
        removeBook.setOnAction( 
            (event) -> {
                System.out.println("Manage: Remove Book");
            }
        );
        finishBook.setOnAction(
            (event) -> {
                System.out.println("Manage: Finish Book");
            }
        );
    }
}
