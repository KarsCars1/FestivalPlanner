package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // The different scenes
        MainScene mainScene = new MainScene(this);
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
            addBandScene.addPerformerPopUp.close();
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
        });
        addBandScene.addButton.setOnAction(E -> {
            if (!addBandScene.performerNameTextField.getText().isEmpty()) {
                mainScene.performerController.addBand(addBandScene.performerNameTextField.getText());
                mainScene.performerController.addBandMembers(addBandScene.newBandMemberList, addBandScene.performerNameTextField.getText());
                mainScene.performerController.updateList(mainScene.performerList);
                addBandScene.newBandMemberList.getItems().clear();
                addBandScene.performerNameTextField.deleteText(0, addBandScene.performerNameTextField.getText().length());
            }
        });

        mainScene.addPerformer.setOnAction(E -> {
            addPerformerScene.addPerformerPopUp.show();
        });

//        editPerformer.setOnAction(E -> {
//            System.out.println("Edit button pressed");
//            EditArtist editArtist = new EditArtist(selectedPerformer, this, performerController);
//            //editArtist(selectedPerformer);
//        });
        //List components
//        performerlist = new ListView();
//        performerlist.setOnMousePressed(e -> {
//            selectedPerformer = performerlist.getSelectionModel().getSelectedItem();
//            //performerController.editPerformer(e.g);
//
//        });

//        addShow.setOnAction(E -> {
//            System.out.println("opening");
//            Stage addPerformanceStage = new Stage();
//            addPerformanceStage.setScene(new AddShowScene(performerController, performerlist.getSelectionModel().getSelectedItem()).getScene());
//            addPerformanceStage.setResizable(false);
//            addPerformanceStage.show();
//        });

        primaryStage.setTitle("Festival planner agenda");
        primaryStage.setScene(mainScene.getScene());
        primaryStage.show();
    }

    public void updateScene(Scene newScene) {
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setHeight(200);
        newStage.setWidth(300);
        newStage.show();
    }
}
