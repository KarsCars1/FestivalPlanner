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

        //Button eventhandling
        addPerformerScene.bandRadioButton.setOnAction(E -> {
            addPerformerScene.name.setText("Band name:");
            addPerformerScene.membersHBox.setVisible(true);
            addPerformerScene.newBandMemberList.setVisible(true);
        });
        addPerformerScene.artistRadioButton.setOnAction(E -> {
            addPerformerScene.name.setText("Artist name:");
            addPerformerScene.membersHBox.setVisible(false);
            addPerformerScene.newBandMemberList.setVisible(false);
        });
        addPerformerScene.backButton.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.close();
        });
        addPerformerScene.addMemberButton.setOnAction(E -> {
            addPerformerScene.newBandMemberList.getItems().add(addPerformerScene.addMemberField.getText());
            addPerformerScene.addMemberField.setText("");
        });
        addPerformerScene.addButton.setOnAction(E -> {
            if (addPerformerScene.artistRadioButton.isSelected()) {
                if (!addPerformerScene.performerNameTextField.getText().isEmpty()) {
                    mainScene.performerController.addArtist(addPerformerScene.performerNameTextField.getText());
                    mainScene.performerController.updateList(mainScene.performerList);
                    addPerformerScene.performerNameTextField.deleteText(0, addPerformerScene.performerNameTextField.getText().length());
                }
            } else if (addPerformerScene.bandRadioButton.isSelected()) {
                if (!addPerformerScene.performerNameTextField.getText().isEmpty()) {
                    mainScene.performerController.addBand(addPerformerScene.performerNameTextField.getText());
                    mainScene.performerController.addBandMembers(addPerformerScene.newBandMemberList, addPerformerScene.performerNameTextField.getText());
                    mainScene.performerController.updateList(mainScene.performerList);
                    addPerformerScene.newBandMemberList.getItems().clear();
                    addPerformerScene.performerNameTextField.deleteText(0, addPerformerScene.performerNameTextField.getText().length());
                }
            }
        });

        //add performer vbox
        addPerformerScene.addPerformerVBox.getChildren().addAll(addPerformerScene.name, addPerformerScene.performerNameTextField, addPerformerScene.artistRadioButton, addPerformerScene.bandRadioButton, addPerformerScene.buttonHBox, addPerformerScene.members, addPerformerScene.membersHBox, addPerformerScene.newBandMemberList);
        addPerformerScene.popUpBorderPane.setTop(addPerformerScene.addPerformerVBox);
        addPerformerScene.popUpBorderPane.setBottom(addPerformerScene.buttonHBox);

        mainScene.addPerformer.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.show();
        });


        primaryStage.setTitle("Festival planner agenda");
        primaryStage.setScene(mainScene.getScene());
        primaryStage.show();
    }
}
