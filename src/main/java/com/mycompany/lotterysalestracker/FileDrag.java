package com.mycompany.lotterysalestracker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileDrag extends StackPane {
    private List listeners = new ArrayList();
    public FileDrag() {
        Rectangle fileInputZone = new Rectangle();
        fileInputZone.setWidth(400.0);
        fileInputZone.setHeight(100.0);
        fileInputZone.setFill(Color.LIGHTGREEN);
        
        fileInputZone.setOnDragEntered((DragEvent event) -> {
                if(event.getSource() == fileInputZone && event.getDragboard().hasFiles()) {
                    fileInputZone.setFill(Color.DARKGREEN);
                }
                event.consume();
            }
        );
        
        fileInputZone.setOnDragExited((DragEvent event) -> {
                fileInputZone.setFill(Color.LIGHTGREEN);
                event.consume();
            }
        );
        
        fileInputZone.setOnDragOver(
            (DragEvent event) -> {
                if(event.getDragboard().hasFiles()) {
                    for (File f: event.getDragboard().getFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                }
            }
        );
        
        fileInputZone.setOnDragDropped(
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
        
        fileInputZone.setOnMouseClicked(new EventHandler<MouseEvent>()
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
        Label instruction = new Label("Click here to select file, or drag and drop a file here");
        this.getChildren().addAll(fileInputZone, instruction);
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