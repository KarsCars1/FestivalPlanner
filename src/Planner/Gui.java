package Planner;

import DataStructure.PerformerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application implements GuiCallback {
    public Stage popUpStage;
    PerformerController performerController = new PerformerController();
    SimulatorScene simulatorScene;
    MainScene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        this.popUpStage = new Stage();
        this.simulatorScene = new SimulatorScene(new Stage());
        // Make the mainScene
        this.mainScene = new MainScene(performerController, this);
        primaryStage.setScene(mainScene.getScene());

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
