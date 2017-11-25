package com.mycompany.lotterysalestracker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileDrag extends Pane {
    private List listeners = new ArrayList();
    public FileDrag() {
        Rectangle rekt = new Rectangle();
        rekt.relocate(50, 50);
        rekt.setWidth(300.0);
        rekt.setHeight(100.0);
        rekt.setFill(Color.LIGHTGREEN);
        
        rekt.setOnDragEntered(
            (DragEvent event) -> {
                if(event.getSource() == rekt && event.getDragboard().hasFiles()) {
                    rekt.setFill(Color.DARKGREEN);
                }
                event.consume();
            }
        );
        
        rekt.setOnDragExited(
            (DragEvent event) -> {
                rekt.setFill(Color.LIGHTGREEN);
                event.consume();
            }
        );
        
        rekt.setOnDragOver(
            (DragEvent event) -> {
                if(event.getDragboard().hasFiles()) {
                    for (File f: event.getDragboard().getFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                }
            }
        );
        
        rekt.setOnDragDropped(
            (DragEvent event) -> {
                if(event.getDragboard().hasFiles()) {
                    for (File f: event.getDragboard().getFiles()) {
                        fireEvent(f.getPath());
                    }
                    event.setDropCompleted(true);
                }
                event.consume();
            }
        );
        
        rekt.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setTitle("Open: Pick a Day");
                chooseFile.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(".json", "*")
                );
                
                File file = chooseFile.showOpenDialog(getOwnerWindow());
                if (file != null) {
                    fireEvent(file.getPath());
                } else {
                    System.out.println("Nothing was selected");
                }
            }
        });
        
        this.getChildren().addAll(rekt);
    }
    public void addListener(FileListener listener) {
        listeners.add(listener);
    }
    public void removeListener(FileListener listener) {
        listeners.remove(listener);
    }
    private void fireEvent(String file) {
        Iterator listeners = this.listeners.iterator();
        while (listeners.hasNext()) {
            ((FileListener) listeners.next()).fileAccepted(file);
        }
    }
    private Window getOwnerWindow() {
        Scene parentScene = this.getScene();
        if (parentScene != null) {
            return parentScene.getWindow();
        }
        return null;
    }
}