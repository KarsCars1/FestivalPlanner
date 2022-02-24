package sample;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddEditBandScene extends StandardScene{

    private PerformerController controller;

    public AddEditBandScene() {
        //member interface in case of band
        Label members = new Label("Members");
        HBox membersHBox = new HBox();
        Button addMemberButton = new Button("+");
        TextField addMemberField = new TextField();

        //add performer popup
        BorderPane popUpBorderPane = new BorderPane();
        ListView<String> newBandMemberList = new ListView<>();

        //add performer vbox
        VBox addPerformerVBox = new VBox();
        Label name = new Label("Band name:");
        TextField performerNameTextField = new TextField();

        //radiobuttons
        Button switchToArtistButton = new Button("Switch to Artist");

        //Button hbox
        HBox buttonHBox = new HBox();
        Button backButton = new Button("Back");
        Button addButton = new Button("Add to list");
        membersHBox.getChildren().addAll(addMemberField, addMemberButton);
        newBandMemberList.setMaxSize(175, 150);
        buttonHBox.getChildren().addAll(backButton, addButton, switchToArtistButton);
        buttonHBox.setSpacing(140);
        addPerformerVBox.getChildren().addAll(name, performerNameTextField, switchToArtistButton, buttonHBox, members, membersHBox, newBandMemberList);
        popUpBorderPane.setTop(addPerformerVBox);
        popUpBorderPane.setBottom(buttonHBox);
        scene = new Scene(popUpBorderPane);
    }
}
