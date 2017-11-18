package com.mycompany.lotterysalestracker;


import com.mycompany.lotterysalestracker.Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GameTable extends ScrollPane{
    
    private TableView<Game> table;
    private final ObservableList<Game> list = FXCollections.observableArrayList(
            new Game("Game1", 0),
            new Game("Game2", 2),
            new Game("Game2", 2),
            new Game("Game2", 2),
            new Game("Game2", 2),
            new Game("Game2", 2)
    );
    
    public GameTable(/*ObservableList<Game> gameList*/){
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameColumn"));
        
        TableColumn gameNumberColumn = new TableColumn("Number");
        gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        
        table.getColumns().addAll(nameColumn, gameNumberColumn);
        table.setItems(list);
        this.getChildren().addAll(table);
    }
}