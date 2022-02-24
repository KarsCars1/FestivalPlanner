package sample;

import DataStructure.Data.Artist;
import DataStructure.Data.Band;
import DataStructure.Data.Performer;
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

        EditArtistScene editArtistScene = new EditArtistScene();
        Stage editArtistStage = new Stage();
        editArtistStage.setScene(editArtistScene.getScene());

        AddShowScene addShowScene = new AddShowScene(mainScene.performerController);
        Stage addPerformanceStage = new Stage();
        addPerformanceStage.setScene(addShowScene.getScene());
        EditBandScene editBandScene = new EditBandScene();
        Stage editBandStage = new Stage();
        editBandStage.setScene(editBandScene.getScene());

        mainScene.addShow.setOnAction(E -> {
            System.out.println("opening");
            addShowScene.setVariables(mainScene.performerList.getSelectionModel().getSelectedItem());
            addPerformanceStage.setResizable(false);
            addPerformanceStage.show();

        });

        addShowScene.getSave().setOnAction(e -> {
            addShowScene.saveShow();
            mainScene.updateShows();
        });
        mainScene.addLocation.setOnAction(E -> {
            Stage addLoactionStage = new Stage();
            addLoactionStage.setScene(new AddLocation(mainScene.performerController).getScene());
            addLoactionStage.show();
        });

        //Button eventhandling
        mainScene.editPerformer.setOnAction(E -> {
            if (!mainScene.performerList.getSelectionModel().isEmpty()) {

                for (Artist artist : mainScene.performerController.getArtists()){
                    editArtistScene.setOldArtist(mainScene.performerList.getSelectionModel().getSelectedItem());
                    if (artist.getPerformerName().equals(editArtistScene.getOldArtist())) {
                        editArtistScene.getArtistField().setText(mainScene.performerList.getSelectionModel().getSelectedItem());
                        editArtistStage.show();
                        break;
                    }
                }
                for (Band band : mainScene.performerController.getBands()) {
                    editBandScene.setOldArtist(mainScene.performerList.getSelectionModel().getSelectedItem());
                    if (band.getPerformerName().equals(editBandScene.getOldArtist())) {
                        editBandScene.getBandField().setText(mainScene.performerList.getSelectionModel().getSelectedItem());
                        editBandStage.show();
                    }
                }

            }
        });
        editArtistScene.getSaveButton().setOnAction(E -> {
            for (Performer performer : mainScene.performerController.getPerformers()) {
                if (performer.getPerformerName().equals(editArtistScene.getOldArtist())) {
                    performer.setPerformerName(editArtistScene.getArtistField().getText());
                }
            }
            mainScene.performerController.updateList(mainScene.performerList);
            editArtistStage.close();
        });
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
            if (!addBandScene.addMemberField.getText().trim().equals("")){
                addBandScene.newBandMemberList.getItems().add(addBandScene.addMemberField.getText());
            }
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

        primaryStage.setTitle("Festival planner agenda");
        primaryStage.setScene(mainScene.getScene());
        primaryStage.show();
    }
}
