package sample;

import DataStructure.Data.Artist;
//import javafx.*;
//import javafx.application.Application;
import DataStructure.Data.Performer;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

//import javax.swing.*;

public class EditArtist extends StandardScene {
    private PerformerController controller;
    private String artist;
    private TextField artistField;

    public EditArtist(String artist, Gui gui, PerformerController controller){
        this.controller = controller;
        this.artist = artist;
        GridPane pane = new GridPane();

        this.artistField = new TextField(artist);
        Button saveButton = new Button("Save Changes");
        pane.add(artistField, 0, 0);
        pane.add(saveButton, 0, 1);

        Scene scene = new Scene(pane);
        gui.updateScene(scene);

    }

    public void saveChanges(){
        controller.getPerformers();
    }

}


