package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddPerformerScene extends StandardScene {
    //add performer popup
    BorderPane popUpBorderPane = new BorderPane();
    ListView<String> newBandMemberList = new ListView<>();

    //add performer vbox
    VBox addPerformerVBox = new VBox();
    Label name = new Label("Artist name:");
    TextField performerNameTextField = new TextField();

    //radiobutton
    Button switchToBandButton = new Button("Switch to Band");

    //Button hbox
    HBox buttonHBox = new HBox();
    Button backButton = new Button("Back");
    Button addButton = new Button("Add to list");

    public AddPerformerScene(){
        buttonHBox.getChildren().addAll(backButton, switchToBandButton, addButton);
        buttonHBox.setSpacing(140);
        addPerformerVBox.getChildren().addAll(name, performerNameTextField, switchToBandButton);
        popUpBorderPane.setTop(addPerformerVBox);
        popUpBorderPane.setBottom(buttonHBox);
        scene = new Scene(popUpBorderPane);
    }
}
