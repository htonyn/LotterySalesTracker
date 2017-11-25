package com.mycompany.lotterysalestracker;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {
    Manage manage;
    Scan scan;
    Stats stats;
    VBox content;
    
    @Override
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();        
        content = new VBox();
        root.setHgrow(content, Priority.ALWAYS);
        root.setVgrow(content, Priority.ALWAYS);
        VBox navList = new VBox(2);
        manage = new Manage();
        scan = new Scan();
        stats = new Stats();
        root.add(navList, 0, 0);
        root.add(content, 1, 0);
        root.setVgrow(navList, Priority.ALWAYS);
        content.setFillWidth(true);      
        navList.setMinWidth(100);
        
        Button manageNav = new Button("Manage");
        manageNav.setMaxWidth(Double.MAX_VALUE);
        Button scanNav = new Button("Scan");
        scanNav.setMaxWidth(Double.MAX_VALUE);
        Button dataNav = new Button("Data");
        dataNav.setMaxWidth(Double.MAX_VALUE);
        
        navList.getChildren().addAll(manageNav, scanNav, dataNav);
        
        manageNav.setOnAction(
            (event) -> {
                content.getChildren().clear();
//                System.out.println("Manage");
                content.getChildren().add(manage);
                content.setVgrow(manage, Priority.ALWAYS);
                
            }
        );
        scanNav.setOnAction(
            (event) -> {
                content.getChildren().clear();
//                System.out.println("Scan");
                content.getChildren().add(scan);
            }
        );
        dataNav.setOnAction(
            (event) -> {
                content.getChildren().clear();
//                System.out.println("Data");
                content.getChildren().add(stats);
            }
        );
        
        
        Scene scene = new Scene(root, 500, 500);
        
        stage.setTitle("Lottery Sales Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
