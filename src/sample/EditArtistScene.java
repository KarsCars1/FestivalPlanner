package sample;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//import javafx.*;
//import javafx.application.Application;

//import javax.swing.*;

public class EditArtistScene extends StandardScene {
    private String artist;
    private String oldArtist;
    Stage editPerformerPopup = new Stage();
    private TextField artistField = new TextField();

    public String getOldArtist() {
        return oldArtist;
    }

    public TextField getArtistField() {
        return artistField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setOldArtist(String oldArtist) {
        this.oldArtist = oldArtist;
    }


    private Button saveButton = new Button("Save Changes");
    private BorderPane pane = new BorderPane();

    public EditArtistScene() {
        scene = new Scene(pane);
        pane.setLeft(artistField);
        pane.setRight(saveButton);
        editPerformerPopup.setScene(scene);
    }
}


