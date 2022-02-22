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
        Button removePerformer = new Button("Remove performer");
        Button editPerformer = new Button("Edit performer");

        Button addShow = new Button("Add Show");

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

    public void editArtist(String artist) {
        Stage editArtistPopUp = new Stage();
        BorderPane popUpBorderPane = new BorderPane();
        Scene popUpScene = new Scene(popUpBorderPane);
        editArtistPopUp.setScene(popUpScene);
        editArtistPopUp.setHeight(350);
        editArtistPopUp.setWidth(300);
        editArtistPopUp.show();


    }
}
