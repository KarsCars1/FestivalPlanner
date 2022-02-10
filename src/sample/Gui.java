package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import DataStructure.PerformerController;

public class Gui extends Application {

    private PerformerController performerController = new PerformerController();
    ListView<String> performerlist = new ListView<>();
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
        TextField artistNameTextField = new TextField();
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
        membersHBox.setVisible(false);

        //Button hbox
        HBox buttonHbox = new HBox();
        buttonHbox.setSpacing(140);
        Button backButton = new Button("Back");
        Button addButton = new Button("Add to list");
        buttonHbox.getChildren().addAll(backButton, addButton);

        //Button eventhandling
        bandRadioButton.setOnAction(E -> {
            name.setText("Band name:");
            members.setVisible(true);
            membersHBox.setVisible(true);

        });
        artistRadioButton.setOnAction(E -> {
            name.setText("Artist name:");
            members.setVisible(false);
            membersHBox.setVisible(false);
        });
        backButton.setOnAction(E -> {
            addPerformerPopUp.close();
        });
        addButton.setOnAction(E->{
            if (!artistNameTextField.getText().isEmpty()) {
                performerController.addArtist(artistNameTextField.getText());
                performerlist.getItems().add(artistNameTextField.getText());
                artistNameTextField.deleteText(0, artistNameTextField.getText().length());
            }
        });

        //add performer vbox
        addPerformerVbox.getChildren().addAll(name, artistNameTextField, artistRadioButton, bandRadioButton, buttonHbox, members, membersHBox);
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
                    System.out.println("Pressed");
                    addPerformerPopUp.show();
                });
        Button removePerformer = new Button("Remove performer");
        Button updatePerfomer = new Button("Update performer");
        //List components
        performerlist = new ListView();
        //performerlist.getItems().addAll("test", "test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test");

        performerlist.setOrientation(Orientation.VERTICAL);

        performerVBox.getChildren().addAll(performerLabel, performerlist, addPerformer, updatePerfomer, removePerformer);
        agendaBorderpane.setRight(performerVBox);
        agendaBorderpane.setLeft(textField);
        Scene agendaScene = new Scene(agendaBorderpane);
        primaryStage.setScene(agendaScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
