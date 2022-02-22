package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // The different scenes
        MainScene mainScene = new MainScene();
        AddPerformerScene addPerformerScene = new AddPerformerScene();
        AddBandScene addBandScene = new AddBandScene();

        //Button eventhandling
        addPerformerScene.switchToBandButton.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.close();
            addBandScene.addPerformerPopUp.show();
        });
        addBandScene.switchToArtistButton.setOnAction(E -> {
            addBandScene.addPerformerPopUp.close();
            addPerformerScene.addPerformerPopUp.show();
        });
        addPerformerScene.backButton.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.close();
        });
        addBandScene.backButton.setOnAction(E -> {

        });
        addBandScene.addMemberButton.setOnAction(E -> {
            addBandScene.newBandMemberList.getItems().add(addBandScene.addMemberField.getText());
            addBandScene.addMemberField.setText("");
        });
        addPerformerScene.addButton.setOnAction(E -> {
            if (!addPerformerScene.performerNameTextField.getText().isEmpty()) {
                mainScene.performerController.addArtist(addPerformerScene.performerNameTextField.getText());
                mainScene.performerController.updateList(mainScene.performerList);
                addPerformerScene.performerNameTextField.deleteText(0, addPerformerScene.performerNameTextField.getText().length());
            }
            if (!addBandScene.performerNameTextField.getText().isEmpty()) {
                mainScene.performerController.addBand(addBandScene.performerNameTextField.getText());
                mainScene.performerController.addBandMembers(addBandScene.newBandMemberList, addBandScene.performerNameTextField.getText());
                mainScene.performerController.updateList(mainScene.performerList);
                addBandScene.newBandMemberList.getItems().clear();
                addBandScene.performerNameTextField.deleteText(0, addBandScene.performerNameTextField.getText().length());
            }
        });

        //add performer vbox
        addPerformerScene.addPerformerVBox.getChildren().addAll(addPerformerScene.name, addPerformerScene.performerNameTextField, addPerformerScene.switchToBandButton);
        addPerformerScene.popUpBorderPane.setTop(addPerformerScene.addPerformerVBox);
        addPerformerScene.popUpBorderPane.setBottom(addPerformerScene.buttonHBox);

        addBandScene.addPerformerVBox.getChildren().addAll(addBandScene.name, addBandScene.performerNameTextField, addBandScene.switchToArtistButton, addBandScene.buttonHBox, addBandScene.members, addBandScene.membersHBox, addPerformerScene.newBandMemberList);
        addBandScene.popUpBorderPane.setTop(addBandScene.addPerformerVBox);
        addBandScene.popUpBorderPane.setBottom(addBandScene.buttonHBox);


        mainScene.addPerformer.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.show();
        });


        primaryStage.setTitle("Festival planner agenda");
        primaryStage.setScene(mainScene.getScene());
        primaryStage.show();
    }
}
