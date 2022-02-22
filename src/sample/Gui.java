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
        Stage mainStage = new Stage();
        mainStage.setScene(mainScene.getScene());

        AddPerformerScene addPerformerScene = new AddPerformerScene();
        Stage addPerformerStage = new Stage();
        addPerformerStage.setScene(addPerformerScene.getScene());

        AddBandScene addBandScene = new AddBandScene();
        Stage addBandStage = new Stage();
        addBandStage.setScene(addBandScene.getScene());

        EditArtist editArtist = new EditArtist(mainScene.selectedPerformer, mainScene.performerController);
        Stage editArtistStage = new Stage();
        editArtistStage.setScene(editArtist.getScene());


        mainScene.editPerformer.setOnAction(E -> {
            System.out.println("Edit button pressed");
            editArtistStage.show();
        });

        mainScene.addShow.setOnAction(E -> {
            System.out.println("opening");
            Stage addPerformanceStage = new Stage();
            addPerformanceStage.setScene(new AddShowScene(mainScene.performerController, mainScene.performerList.getSelectionModel().getSelectedItem()).getScene());
            addPerformanceStage.setResizable(false);
            addPerformanceStage.show();
        });
        mainScene.addLocation.setOnAction(E -> {
            Stage addLoactionStage = new Stage();
            addLoactionStage.setScene(new AddLocation(mainScene.performerController).getScene());
            addLoactionStage.show();
        });

        //Button eventhandling
        addPerformerScene.switchToBandButton.setOnAction(E -> {
            addPerformerStage.close();
            addBandStage.show();
        });
        addBandScene.switchToArtistButton.setOnAction(E -> {
            addBandStage.close();
            addPerformerStage.show();
        });
        addPerformerScene.backButton.setOnAction(E -> {
            addPerformerStage.close();
        });
        addBandScene.backButton.setOnAction(E -> {
            addBandStage.close();
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
            addPerformerStage.show();
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
}
