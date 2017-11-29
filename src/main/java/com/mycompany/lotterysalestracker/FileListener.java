package com.mycompany.lotterysalestracker;
// ====================================================================== //
// Interface for the FileDragger to display the TableView for a given
// TableView.
// Usage: FileDrag contains FileListener and calls the method upon
// a file being selected. Then, any listeners will receive the file path
// of the file as a String which the Stats module receives and implements
// this interface in order to generate a new TableView with that data
// ====================================================================== //
public interface FileListener {
    void fileAccepted(String path);
}

