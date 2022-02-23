package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditBandScene extends StandardScene{
    private Label members = new Label("Members:");
    private TextField performerNameTextField = new TextField();
    private VBox addPerformerVBox = new VBox();
    private String band;
    private String oldArtist;
    private Stage editPerformerPopup = new Stage();
    private TextField bandField = new TextField();
    private HBox buttonHBox = new HBox();
    private ListView newBandMemberList = new ListView();
    private Button addMemberButton = new Button("+");
    private TextField addMemberField = new TextField();
    private HBox membersHBox = new HBox();
    private Button removeMember = new Button();

    public String getOldArtist() {
        return oldArtist;
    }

    public TextField getBandField() {
        return bandField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setOldArtist(String oldArtist) {
        this.oldArtist = oldArtist;
    }


    private Button saveButton = new Button("Save Changes");
    private BorderPane pane = new BorderPane();

    public EditBandScene() {
        pane.setRight(saveButton);
        membersHBox.getChildren().addAll(addMemberField, addMemberButton);
        newBandMemberList.setMaxSize(175, 150);
        buttonHBox.getChildren().addAll(removeMember, saveButton);
        buttonHBox.setSpacing(140);
        addPerformerVBox.getChildren().addAll(bandField, buttonHBox, members, membersHBox, newBandMemberList);
        pane.setTop(addPerformerVBox);
        pane.setBottom(buttonHBox);
        scene = new Scene(pane);
        editPerformerPopup.setScene(scene);
    }
}
