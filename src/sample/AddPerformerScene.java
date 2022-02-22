package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPerformerScene extends StandardScene {
    //add performer popup
    Stage addPerformerPopUp = new Stage();
    BorderPane popUpBorderPane = new BorderPane();
    ListView<String> newBandMemberList = new ListView<>();

    //add performer vbox
    VBox addPerformerVBox = new VBox();
    Label name = new Label("Artist name:");
    TextField performerNameTextField = new TextField();

    //radiobuttons
    ToggleGroup selectorToggleGroup = new ToggleGroup();
    RadioButton artistRadioButton = new RadioButton("Artist");
    RadioButton switchToBandButton = new RadioButton("Band");

    //Button hbox
    HBox buttonHBox = new HBox();
    Button backButton = new Button("Back");
    Button addButton = new Button("Add to list");

    public AddPerformerScene(){
        scene = new Scene(popUpBorderPane);
        addPerformerPopUp.setScene(scene);
        addPerformerPopUp.setHeight(350);
        addPerformerPopUp.setWidth(300);
        artistRadioButton.setToggleGroup(selectorToggleGroup);
        switchToBandButton.setToggleGroup(selectorToggleGroup);
        buttonHBox.getChildren().addAll(backButton, addButton);
        buttonHBox.setSpacing(140);
    }
}