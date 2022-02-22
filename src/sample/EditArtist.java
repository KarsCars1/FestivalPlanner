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
    private String oldArtist;
    Stage editPerformerPopup = new Stage();
    private TextField artistField = new TextField();

    public String getArtist() {
        return artist;
    }

    public String getOldArtist() {
        return oldArtist;
    }

    public Stage getEditPerformerPopup() {
        return editPerformerPopup;
    }

    public TextField getArtistField() {
        return artistField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public BorderPane getPane() {
        return pane;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setOldArtist(String oldArtist) {
        this.oldArtist = oldArtist;
    }

    public void setEditPerformerPopup(Stage editPerformerPopup) {
        this.editPerformerPopup = editPerformerPopup;
    }

    public void setArtistField(TextField artistField) {
        this.artistField = artistField;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public void setPane(BorderPane pane) {
        this.pane = pane;
    }

    private Button saveButton = new Button("Save Changes");
    private BorderPane pane = new BorderPane();

    public EditArtist(){

        scene = new Scene(pane);
        pane.setLeft(artistField);
        pane.setRight(saveButton);
        editPerformerPopup.setScene(scene);
    }

    public EditArtist(String artist, PerformerController controller){
        this.controller = controller;
        this.artist = artist;


        this.artistField = new TextField(artist);

        pane.getChildren().addAll(artistField);
        pane.getChildren().addAll(saveButton);

        scene = new Scene(pane);
    }

    public void saveChanges(){
        controller.getPerformers();
    }

}


