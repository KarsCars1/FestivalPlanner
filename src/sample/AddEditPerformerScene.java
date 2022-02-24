package sample;

import DataStructure.Data.Artist;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddEditPerformerScene extends StandardScene {

    private PerformerController controller;

    public AddEditPerformerScene(PerformerController controller, Artist artist, GuiCallback callback){

        BorderPane popUpBorderPane = new BorderPane();
        VBox addPerformerVBox = new VBox();
        Label name = new Label("Artist name:");
        TextField performerNameTextField = new TextField();
        Button switchToBandButton = new Button("Switch to Band");
        HBox buttonHBox = new HBox();
        Button backButton = new Button("Back");
        Button addButton = new Button("Add to list");
        Button saveButton = new Button("Save Changes");
        this.controller = controller;

        if (artist == null){
            buttonHBox.getChildren().addAll(backButton, addButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, switchToBandButton);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        } else {
            performerNameTextField.setText(artist.getPerformerName());
            buttonHBox.getChildren().addAll(backButton, saveButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        }

        backButton.setOnAction(e -> {
            callback.closeStage();
        });

        switchToBandButton.setOnAction(e -> {
            callback.setStage(new AddEditBandScene(controller, null, callback).getScene());
        });

        saveButton.setOnAction(e -> {
            artist.setPerformerName(performerNameTextField.getText());
            callback.updateLists();
            callback.closeStage();
        });

        addButton.setOnAction(e -> {
            if (!performerNameTextField.getText().isEmpty()) {
                controller.addArtist(performerNameTextField.getText());
                callback.updateLists();
                performerNameTextField.deleteText(0, performerNameTextField.getText().length());
            }
        });

        scene = new Scene(popUpBorderPane);
    }
}
