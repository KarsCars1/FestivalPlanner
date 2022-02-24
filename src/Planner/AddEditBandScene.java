package Planner;

import DataStructure.Data.Band;
import DataStructure.Data.Performer;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddEditBandScene extends StandardScene {

    private PerformerController controller;

    public AddEditBandScene(PerformerController controller, Band band, GuiCallback callback) {

        this.controller = controller;
        Label members = new Label("Members");
        HBox membersHBox = new HBox();
        Button addMemberButton = new Button("+");
        TextField addMemberField = new TextField();
        BorderPane popUpBorderPane = new BorderPane();
        ListView<String> BandMemberList = new ListView<>();
        VBox addPerformerVBox = new VBox();
        Label name = new Label("Band name:");
        TextField performerNameTextField = new TextField();
        Button switchToArtistButton = new Button("Switch to Artist");
        HBox buttonHBox = new HBox();
        Button backButton = new Button("Back");
        Button addButton = new Button("Add to list");
        Button saveButton = new Button("Save Changes");

        if (band == null) {
            membersHBox.getChildren().addAll(addMemberField, addMemberButton);
            BandMemberList.setMaxSize(175, 150);
            buttonHBox.getChildren().addAll(backButton, addButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, switchToArtistButton, buttonHBox, members, membersHBox, BandMemberList);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        } else {
            performerNameTextField.setText(band.getPerformerName());
            membersHBox.getChildren().addAll(addMemberField, addMemberButton);
            BandMemberList.setMaxSize(175, 150);
            for (Performer member : band.getMembers()) {
                BandMemberList.getItems().add(member.getPerformerName());
            }
            buttonHBox.getChildren().addAll(backButton, saveButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, buttonHBox, members, membersHBox, BandMemberList);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        }

        switchToArtistButton.setOnAction(e -> {
            callback.closeStage();
            callback.setStage(new AddEditPerformerScene(controller, null, callback).getScene());
        });

        backButton.setOnAction(e -> {
            callback.closeStage();
        });

        addMemberButton.setOnAction(E -> {
            if (!addMemberField.getText().trim().equals("")) {
                if (band != null) {
                    band.addMember(new Performer(addMemberField.getText()));
                }
                BandMemberList.getItems().add(addMemberField.getText());
            }
            addMemberField.setText("");
        });

        addButton.setOnAction(E -> {
            if (!performerNameTextField.getText().isEmpty()) {
                controller.addBand(performerNameTextField.getText());
                controller.addBandMembers(BandMemberList, performerNameTextField.getText());
                callback.updateLists();
                BandMemberList.getItems().clear();
                performerNameTextField.deleteText(0, performerNameTextField.getText().length());
            }
        });

        saveButton.setOnAction(e -> {
            int i = 0;
            for (Performer member : band.getMembers()) {
                member.setPerformerName(BandMemberList.getItems().get(i));
                i++;
            }
            band.setPerformerName(performerNameTextField.getText());
            callback.updateLists();
            callback.closeStage();
        });


        scene = new Scene(popUpBorderPane);
    }
}
