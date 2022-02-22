package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddBandScene extends StandardScene{
    //member interface in case of band
    Label members = new Label("Members");
    HBox membersHBox = new HBox();
    Button addMemberButton = new Button("+");
    TextField addMemberField = new TextField();

    //add performer popup
    Stage addPerformerPopUp = new Stage();
    BorderPane popUpBorderPane = new BorderPane();
    ListView<String> newBandMemberList = new ListView<>();

    //add performer vbox
    VBox addPerformerVBox = new VBox();
    Label name = new Label("Artist name:");
    TextField performerNameTextField = new TextField();

    //radiobuttons
    Button switchToArtistButton = new Button("Switch to Artist");

    //Button hbox
    HBox buttonHBox = new HBox();
    Button backButton = new Button("Back");
    Button addButton = new Button("Add to list");

    public AddBandScene() {
        scene = new Scene(popUpBorderPane);
        addPerformerPopUp.setScene(scene);
        addPerformerPopUp.setHeight(500);
        addPerformerPopUp.setWidth(300);
        membersHBox.getChildren().addAll(addMemberField, addMemberButton);
        newBandMemberList = new ListView<>();
        newBandMemberList.setPrefSize(175, 50);
        buttonHBox.getChildren().addAll(backButton, addButton, switchToArtistButton);
        buttonHBox.setSpacing(140);
    }
}
