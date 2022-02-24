package sample;

import DataStructure.Data.Artist;
import DataStructure.Data.Band;
import DataStructure.PerformerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Gui extends Application implements GuiCallback {
    public Stage popUpStage;
    PerformerController performerController = new PerformerController();
    MainScene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        this.popUpStage = new Stage();

        // Make the mainScene
        this.mainScene = new MainScene(performerController,this);
        primaryStage.setScene(mainScene.getScene());

//        AddPerformerScene addPerformerScene = new AddPerformerScene();
//
//        AddEditBandScene addBandScene = new AddEditBandScene();
//
//        EditArtistScene editArtistScene = new EditArtistScene();


        AddShowScene addShowScene = new AddShowScene(mainScene.performerController);
        Stage addPerformanceStage = new Stage();
        addPerformanceStage.setScene(addShowScene.getScene());

        mainScene.addShow.setOnAction(e -> {
            System.out.println("opening");
            addShowScene.setVariables(mainScene.performerList.getSelectionModel().getSelectedItem());
            addPerformanceStage.setResizable(false);
            addPerformanceStage.show();

        });

//        addShowScene.getSave().setOnAction(e -> {
//            addShowScene.saveShow();
//            mainScene.updateShows();
//        });


        //Button eventhandling

//        editArtistScene.getSaveButton().setOnAction(E -> {
//            for (Performer performer : mainScene.performerController.getPerformers()) {
//                if (performer.getPerformerName().equals(editArtistScene.getOldArtist())) {
//                    performer.setPerformerName(editArtistScene.getArtistField().getText());
//                }
//            }
//            mainScene.performerController.updateList(mainScene.performerList);
//            editArtistStage.close();
//        });
//        addPerformerScene.switchToBandButton.setOnAction(E -> {
//            addPerformerStage.close();
//            addBandStage.show();
//        });

//        addPerformerScene.backButton.setOnAction(E -> {
//            addPerformerStage.close();
//        });


//        addPerformerScene.addButton.setOnAction(E -> {
//            if (!addPerformerScene.performerNameTextField.getText().isEmpty()) {
//                mainScene.performerController.addArtist(addPerformerScene.performerNameTextField.getText());
//                mainScene.performerController.updateList(mainScene.performerList);
//                addPerformerScene.performerNameTextField.deleteText(0, addPerformerScene.performerNameTextField.getText().length());
//            }
//        });



        primaryStage.setScene(mainScene.getScene());
        primaryStage.setTitle("Festival planner agenda");
        primaryStage.show();
    }

    @Override
    public void setStage(Scene scene) {
        this.popUpStage.setScene(scene);
        this.popUpStage.setTitle("");
        this.popUpStage.show();
    }

    @Override
    public void setStage(Scene scene, String name) {
        this.popUpStage.setScene(scene);
        this.popUpStage.setTitle(name);
        this.popUpStage.show();
    }

    @Override
    public void closeStage() {
        this.popUpStage.close();
    }

    @Override
    public void updateLists() {
        this.mainScene.updateShows();
        this.mainScene.updatePerformerList();
    }

}
