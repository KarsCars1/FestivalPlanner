package Planner;

import DataStructure.Data.Band;
import DataStructure.Data.Performer;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//the AddEditBandScene is the pop up screen that appears when you want to add or edit a band to or from the list

public class AddEditBandScene extends StandardScene {

    private PerformerController controller;
    private Label members;
    private HBox membersHBox;
    private Button addMemberButton;
    private TextField addMemberField;
    private BorderPane popUpBorderPane;
    private ListView<String> BandMemberList;
    private VBox addPerformerVBox;
    private Label name;
    private TextField performerNameTextField;
    private Label popularityLabel;
    private TextField popularityTextField;
    private Button switchToArtistButton;
    private HBox buttonHBox;
    private Button backButton;
    private Button addButton;
    private Button saveButton;

    public AddEditBandScene(PerformerController controller, Band band, GuiCallback callback) {

        this.controller = controller;
        members = new Label("Members");
        membersHBox = new HBox();
        addMemberButton = new Button("+");
        addMemberField = new TextField();
        popUpBorderPane = new BorderPane();
        BandMemberList = new ListView<>();
        addPerformerVBox = new VBox();
        name = new Label("Band name:");
        performerNameTextField = new TextField();
        popularityLabel = new Label("Popularity (0 to 100): ");
        popularityTextField = new TextField();
        switchToArtistButton = new Button("Switch to Artist");
        buttonHBox = new HBox();
        backButton = new Button("Back");
        addButton = new Button("Add to list");
        saveButton = new Button("Save Changes");

//        this method makes sure the value that gets put into the popularity textfield is between 1 and 100

        popularityTextField.setOnKeyReleased(E -> {
            checkPopularity();
        });

        //        the if-statement checks if a band has been selected to edit or a new band is being added and opens up the right screen based on that

        if (band == null) {
            membersHBox.getChildren().addAll(addMemberField, addMemberButton);
            BandMemberList.setMaxSize(175, 150);
            buttonHBox.getChildren().addAll(backButton, addButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, popularityLabel, popularityTextField, switchToArtistButton, buttonHBox, members, membersHBox, BandMemberList);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        } else {
            int popularityNumber = band.getPopularity();
            performerNameTextField.setText(band.getPerformerName());
            popularityTextField.setText(Integer.toString(popularityNumber));
            membersHBox.getChildren().addAll( addMemberField, addMemberButton);
            BandMemberList.setMaxSize(175, 150);
            for (Performer member : band.getMembers()) {
                BandMemberList.getItems().add(member.getPerformerName());
            }
            buttonHBox.getChildren().addAll(backButton, saveButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, popularityLabel, popularityTextField, buttonHBox, members, membersHBox, BandMemberList);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        }

        switchToArtistButton.setOnAction(e -> {
            callback.closeStage();
            callback.setStage(new AddEditPerformerScene(controller, null, callback).getScene());
        });

        backButton.setOnAction(e -> {
            callback.closeStage();
        });

        addMemberButton.setOnAction(E -> {
            if (!addMemberField.getText().trim().equals("")) {
                if (band != null) {
                    band.addMember(new Performer(addMemberField.getText(), 0));
                }
                BandMemberList.getItems().add(addMemberField.getText());
            }
            addMemberField.setText("");
        });

        addButton.setOnAction(E -> {
            if (!performerNameTextField.getText().isEmpty() && !popularityTextField.getText().isEmpty()) {
                controller.addBand(performerNameTextField.getText(), Integer.parseInt(popularityTextField.getText()));
                controller.addBandMembers(BandMemberList, performerNameTextField.getText());
                callback.updateLists();
                BandMemberList.getItems().clear();
                performerNameTextField.deleteText(0, performerNameTextField.getText().length());
                popularityTextField.deleteText(0, popularityTextField.getText().length());
            }
        });

        saveButton.setOnAction(e -> {
            int i = 0;
            for (Performer member : band.getMembers()) {
                member.setPerformerName(BandMemberList.getItems().get(i));
                i++;
            }
            band.setPerformerName(performerNameTextField.getText());
            callback.updateLists();
            callback.closeStage();
        });


        scene = new Scene(popUpBorderPane);
    }

    public void checkPopularity() {
        String text = popularityTextField.getText();
            try {
                int value = Integer.parseInt(text);
               if(value > 100 || value < 0){
                   popularityTextField.setText("");
               }
            } catch (NumberFormatException e) {
                popularityTextField.setText("");
            }

    }
}
