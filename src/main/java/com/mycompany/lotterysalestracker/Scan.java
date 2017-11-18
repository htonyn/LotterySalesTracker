/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lotterysalestracker;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Scan extends GridPane {
    private String gameName;
    public Scan() {
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
        Text gameName = new Text("Scan Game");
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
        HBox gameIdRow = new HBox();
        gameIdRow.getChildren().addAll(new Text("Game #"), r1, new Text("VALUE"));
        gameIdRow.setHgrow(r1, Priority.ALWAYS);
        HBox bookNumRow = new HBox();
        bookNumRow.getChildren().addAll(new Text("Book #"), r2, new Text("VALUE"));
        bookNumRow.setHgrow(r2, Priority.ALWAYS);
        HBox numSoldRow = new HBox();
        numSoldRow.getChildren().addAll(new Text("# Sold"), r3, new Text("VALUE"));
        numSoldRow.setHgrow(r3, Priority.ALWAYS);
        HBox totalValueRow = new HBox();
        totalValueRow.getChildren().addAll(new Text("Total"), r4, new Text("VALUE"));
        totalValueRow.setHgrow(r4, Priority.ALWAYS);
        
        labels.getChildren().addAll(
            gameIdRow, bookNumRow, numSoldRow, totalValueRow
        );
    }
}
