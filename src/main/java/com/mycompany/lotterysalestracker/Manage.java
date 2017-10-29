package com.mycompany.lotterysalestracker;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Manage extends GridPane {
    
    public Manage() {
        initialize();
        Button addGame = new Button("Add");
        Button removeGame = new Button("Remove");
        Button finishBook = new Button("Finish Book");
        add(addGame, 0, 3);
        add(removeGame, 1, 3);
        add(finishBook, 2, 3);
        Region r = new Region();
        add(r, 0, 1);
        setVgrow(r, Priority.ALWAYS);
        setHgrow(r, Priority.ALWAYS);
        
        
        VBox vbox1 = new VBox(8);
        setHgrow(vbox1, Priority.ALWAYS);
        VBox vbox2 = new VBox(8);
        
        TabPane tp = new TabPane();
        
        Tab gamePage = new Tab("All Games");
        gamePage.setContent(vbox1);
        Tab currentPage = new Tab("Current Games");
        currentPage.setContent(vbox2);
        
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
