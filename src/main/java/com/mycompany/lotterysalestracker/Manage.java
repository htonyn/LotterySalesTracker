package com.mycompany.lotterysalestracker;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Manage extends GridPane {
    public Manage() {
        initialize();
        Button addGame = new Button("Add Game");
        Button removeGame = new Button("Remove Game");
        Button finishBook = new Button("Finish Book");
        
        GridPane addGameBox = new GridPane();
        GridPane addGameControls = new GridPane();
        addGameBox.setPrefHeight(800);
        
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
        
        VBox vbox2 = new VBox(8);
        
        TabPane tp = new TabPane();
        tp.setStyle("-fx-background-color: white;");
        tp.setPrefWidth(9999);
        tp.setPrefHeight(9999);
        
        Tab gamePage = new Tab("All Games");
        gamePage.setContent(addGameBox);
        gamePage.setOnCloseRequest(e -> e.consume());
        Tab currentPage = new Tab("Current Books");
        currentPage.setContent(vbox2);
        currentPage.setOnCloseRequest(e -> e.consume());
        
        add(tp, 0, 0, 3, 1);
        setHgrow(tp, Priority.ALWAYS);
        tp.getTabs().addAll(gamePage, currentPage);
        
        addGame.setOnAction(
            (event) -> {
                System.out.println("Add Game");
            }
        );
        removeGame.setOnAction(
            (event) -> {
                System.out.println("Remove Game");
            }
        );
        finishBook.setOnAction(
            (event) -> {
                System.out.println("Finish Book");
            }
        );
    }
    
    public void initialize() {
        // TableView
    }
}
