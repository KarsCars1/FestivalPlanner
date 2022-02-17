package sample;

import DataStructure.PerformerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui extends Application {

    ListView<String> performerlist = new ListView<>();
    ListView<String> newBandMemberlist = new ListView<>();
    private PerformerController performerController = new PerformerController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //add performer popup
        Stage addPerformerPopUp = new Stage();
        BorderPane popUpBorderPane = new BorderPane();
        Scene popUpScene = new Scene(popUpBorderPane);
        addPerformerPopUp.setScene(popUpScene);
        addPerformerPopUp.setHeight(350);
        addPerformerPopUp.setWidth(300);

        //add performer vbox
        VBox addPerformerVbox = new VBox();
        Label name = new Label("Artist name:");
        TextField performerNameTextField = new TextField();
        //radiobuttons
        ToggleGroup selectorToggleGroup = new ToggleGroup();
        RadioButton artistRadioButton = new RadioButton("Artist");
        RadioButton bandRadioButton = new RadioButton("Band");
        artistRadioButton.setToggleGroup(selectorToggleGroup);
        bandRadioButton.setToggleGroup(selectorToggleGroup);
        //member interface in case of band
        Label members = new Label("Members");
        members.setVisible(false);
        HBox membersHBox = new HBox();
        Button addMemberButton = new Button("+");
        TextField addMemberField = new TextField();
        membersHBox.getChildren().addAll(addMemberField, addMemberButton);
        newBandMemberlist.setVisible(false);
        newBandMemberlist = new ListView<>();
        newBandMemberlist.setMaxSize(175, 150);
        membersHBox.setVisible(false);
        newBandMemberlist.setVisible(false);
        artistRadioButton.setSelected(true);

        //Button hbox
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(140);
        Button backButton = new Button("Back");
        Button addButton = new Button("Add to list");
        buttonHbox.getChildren().addAll(backButton, addButton);

        //Button eventhandling
        bandRadioButton.setOnAction(E -> {
            name.setText("Band name:");
            membersHBox.setVisible(true);
            newBandMemberlist.setVisible(true);

        });
        artistRadioButton.setOnAction(E -> {
            name.setText("Artist name:");
            membersHBox.setVisible(false);
            newBandMemberlist.setVisible(false);
        });
        backButton.setOnAction(E -> {
            addPerformerPopUp.close();
        });
        addMemberButton.setOnAction(E -> {
            newBandMemberlist.getItems().add(addMemberField.getText());
            addMemberField.setText("");
        });
        addButton.setOnAction(E -> {
            if (artistRadioButton.isSelected()) {
                if (!performerNameTextField.getText().isEmpty()) {
                    performerController.addArtist(performerNameTextField.getText());
                    performerController.updateList(performerlist);
                    performerNameTextField.deleteText(0, performerNameTextField.getText().length());
                }
            } else if (bandRadioButton.isSelected()) {
                if (!performerNameTextField.getText().isEmpty()) {
                    performerController.addBand(performerNameTextField.getText());
                    performerController.addBandMembers(newBandMemberlist,performerNameTextField.getText());
                    performerController.updateList(performerlist);
                    newBandMemberlist.getItems().clear();
                    performerNameTextField.deleteText(0, performerNameTextField.getText().length());
                }
            }
        });

        //add performer vbox
        addPerformerVbox.getChildren().addAll(name, performerNameTextField, artistRadioButton, bandRadioButton, buttonHbox, members, membersHBox, newBandMemberlist);
        popUpBorderPane.setTop(addPerformerVbox);
        popUpBorderPane.setBottom(buttonHbox);

        //stage title and main borderpane
        primaryStage.setTitle("Festival planner agenda");
        BorderPane agendaBorderpane = new BorderPane();


        //Todo 2dGraphics
        TextField textField = new TextField();

        //Vbox components
        VBox performerVBox = new VBox();
        Label performerLabel = new Label("Performers:");
        Button addPerformer = new Button("Add performer");

        addPerformer.setOnAction(E -> {
            addPerformerPopUp.show();
        });
        Button removePerformer = new Button("Remove performer");

        Button updatePerfomer = new Button("Update performer");
        //List components
        performerlist = new ListView();
        performerlist.setOrientation(Orientation.VERTICAL);
        removePerformer.setOnAction(E ->{
            System.out.println(performerlist.getSelectionModel().getSelectedItem());
            performerController.removePerformer(performerlist.getSelectionModel().getSelectedItem()+"");
            performerController.updateList(performerlist);
        });

        performerVBox.getChildren().addAll(performerLabel, performerlist, addPerformer, updatePerfomer, removePerformer);
        agendaBorderpane.setRight(performerVBox);
        agendaBorderpane.setLeft(textField);
        Scene agendaScene = new Scene(agendaBorderpane);
        primaryStage.setScene(agendaScene);
        primaryStage.show();
    }
}
