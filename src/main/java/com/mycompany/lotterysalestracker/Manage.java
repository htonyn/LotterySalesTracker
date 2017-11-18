package com.mycompany.lotterysalestracker;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Manage extends GridPane {
    GridPane addGameBox;
    GridPane currentGameBox;
    final String cssDefault = "-fx-border-color: blue;\n"
                + "-fx-border-insets: 5;\n"
                + "-fx-border-width: 3;\n"
                + "-fx-border-style: dashed;\n";
    public Manage() {
        addGameBox = new GridPane();
        addGameBox.setPrefHeight(800);
        currentGameBox = new GridPane();
        initializeView();
        populateTableView();
        
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
    
    public void populateTableView() {
        GameTable gt = new GameTable();
//        TableView table = new TableView();
//        TableColumn col1 = new TableColumn("Potato1");
//        TableColumn col2 = new TableColumn("Potato2");
//        table.getColumns().addAll(col1, col2);
//        table.setPrefWidth(800);
//        table.setPrefHeight(800);
//        table.setStyle(cssDefault);
        addGameBox.add(gt, 0, 1, 3, 1);
        addGameBox.setVgrow(gt, Priority.ALWAYS);
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
                System.out.println("Add Game");
            }
        );
        removeGame.setOnAction(
            (event) -> {
                System.out.println("Remove Game");
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
        currentGameControls.add(removeBook, 0, 0);
        currentGameControls.add(r2, 3, 0);
        currentGameControls.add(finishBook, 4, 0);
        currentGameControls.setHgrow(r1, Priority.ALWAYS);
        currentGameControls.setHgrow(r2, Priority.ALWAYS);
        currentGameBox.add(new Text("Current Books"), 0, 0);
        currentGameBox.add(currentGameControls, 0, 3);
        currentGameBox.add(vSpace1, 0, 3);
        currentGameBox.setVgrow(vSpace1, Priority.ALWAYS);
        
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
