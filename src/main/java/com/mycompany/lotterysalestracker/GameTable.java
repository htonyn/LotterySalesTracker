package com.mycompany.lotterysalestracker;


import com.mycompany.backend.DatabaseTO;
import com.mycompany.lotterysalestracker.Model.Game;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GameTable extends ScrollPane {
    private TableView<Game> table = new TableView();
    
    public GameTable(){
        List<Game> games = DatabaseTO.getGames();
        ObservableList<Game> list = FXCollections.observableArrayList(games);
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn gameNumberColumn = new TableColumn("Game Number");
        gameNumberColumn.setCellValueFactory(new PropertyValueFactory<>("gameNumber"));
        
        TableColumn gameValueColumn = new TableColumn("Game Value");
        gameValueColumn.setCellValueFactory(new PropertyValueFactory<>("gameValue"));
        
        table.getColumns().addAll(nameColumn, gameNumberColumn, gameValueColumn);
        table.setItems(list);
        table.setPrefWidth(700);
        this.setPrefWidth(800);
        this.setContent(table);
    }
}