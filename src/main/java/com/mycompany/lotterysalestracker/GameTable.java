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
    private ObservableList<Game> list;
    public GameTable(){
        List<Game> games = DatabaseTO.getGames();
        list = FXCollections.observableArrayList(games);
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
    // ====================================================================== //
    // Returns the selected game in the GameTable TableView
    // Main purpose is used for removing games.
    // ====================================================================== //
    public Game getSelectedItem() {
        if(table.getSelectionModel().getSelectedItem() != null) {
            return table.getSelectionModel().getSelectedItem();
        }
        return null;
    }
    // ====================================================================== //
    // Upon adding or removing a game to the game list, this function will
    // update the GameTable TableView so changes are seen immediately without
    // reinitializing the object and readding it to the view.
    // Future Update: In MVC, the tableView will be listening to one central
    // observable list thus removing the need for this.
    // ====================================================================== //
    public void setItems(List<Game> games) {
        list = FXCollections.observableArrayList(games);
        table.setItems(list);
    }
}