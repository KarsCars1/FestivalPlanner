package sample;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

//import javafx.*;
//import javafx.application.Application;

//import javax.swing.*;

public class EditArtist extends StandardScene {
    private PerformerController controller;
    private String artist;
    private TextField artistField;

    public EditArtist(String artist, PerformerController controller){
        this.controller = controller;
        this.artist = artist;
        GridPane pane = new GridPane();

        this.artistField = new TextField(artist);
        Button saveButton = new Button("Save Changes");
        pane.add(artistField, 0, 0);
        pane.add(saveButton, 0, 1);

        scene = new Scene(pane);
    }

    public void saveChanges(){
        controller.getPerformers();
    }

}


