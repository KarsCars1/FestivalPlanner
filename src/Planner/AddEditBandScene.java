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
    private Button removeButton;

    public AddEditBandScene(PerformerController controller, Band band, GuiCallback callback) {

        this.controller = controller;
        this.members = new Label("Members");
        this.membersHBox = new HBox();
        this.addMemberButton = new Button("+");
        this.addMemberField = new TextField();
        this.popUpBorderPane = new BorderPane();
        this.BandMemberList = new ListView<>();
        this.addPerformerVBox = new VBox();
        this.name = new Label("Band name:");
        this.performerNameTextField = new TextField();
        this.popularityLabel = new Label("Popularity (0 to 100): ");
        this.popularityTextField = new TextField();
        this.switchToArtistButton = new Button("Switch to Artist");
        this.buttonHBox = new HBox();
        this.backButton = new Button("Back");
        this.addButton = new Button("Add to list");
        this.removeButton = new Button("Remove member");
        this.saveButton = new Button("Save Changes");


        this.popularityTextField.setOnKeyReleased(E -> {
            checkPopularity();
        });

        if (band == null) {
            this.membersHBox.getChildren().addAll(this.addMemberField, this.addMemberButton);
            this.BandMemberList.setMaxSize(175, 150);
            this.buttonHBox.getChildren().addAll(this.backButton, this.removeButton, this.addButton);
            this.buttonHBox.setSpacing(10);
            this.addPerformerVBox.getChildren().addAll(this.name, this.performerNameTextField, this.popularityLabel, this.popularityTextField,
                                                       this.switchToArtistButton, this.buttonHBox, this.members, this.membersHBox, this.BandMemberList);
            this.popUpBorderPane.setTop(this.addPerformerVBox);
            this.popUpBorderPane.setBottom(this.buttonHBox);
        } else {
            int popularityNumber = band.getPopularity();
            this.performerNameTextField.setText(band.getPerformerName());
            this.popularityTextField.setText(Integer.toString(popularityNumber));
            this.membersHBox.getChildren().addAll( this.addMemberField, this.addMemberButton);
            this.BandMemberList.setMaxSize(175, 150);
            for (Performer member : band.getMembers()) {
                this.BandMemberList.getItems().add(member.getPerformerName());
            }
            this.buttonHBox.getChildren().addAll(this.backButton,this.removeButton, this.saveButton);
            this.buttonHBox.setSpacing(10);
            this.addPerformerVBox.getChildren().addAll(this.name, this.performerNameTextField, this.popularityLabel, this.popularityTextField,
                                                       this.buttonHBox, this.members, this.membersHBox, this.BandMemberList);
            this.popUpBorderPane.setTop(this.addPerformerVBox);
            this.popUpBorderPane.setBottom(this.buttonHBox);
        }

        this.switchToArtistButton.setOnAction(e -> {
            callback.closeStage();
            callback.setStage(new AddEditPerformerScene(this.controller, null, callback).getScene());
        });

        this.backButton.setOnAction(e -> {
            callback.closeStage();
        });

        this.addMemberButton.setOnAction(E -> {
            if (!this.addMemberField.getText().trim().equals("")) {
                if (band != null) {
                    band.addMember(new Performer(this.addMemberField.getText(), 0));
                }
                this.BandMemberList.getItems().add(this.addMemberField.getText());
            }
            this.addMemberField.setText("");
        });

        this.addButton.setOnAction(E -> {
            if (!this.performerNameTextField.getText().isEmpty() && !this.popularityTextField.getText().isEmpty()) {
                this.controller.addBand(this.performerNameTextField.getText(), Integer.parseInt(this.popularityTextField.getText()));
                this.controller.addBandMembers(this.BandMemberList, this.performerNameTextField.getText());
                callback.updateLists();
                this.BandMemberList.getItems().clear();
                this.performerNameTextField.deleteText(0, this.performerNameTextField.getText().length());
                this.popularityTextField.deleteText(0, this.popularityTextField.getText().length());
            }
        });

        this.removeButton.setOnAction(e -> {
            String removeMember = this.BandMemberList.getSelectionModel().getSelectedItem();
            this.BandMemberList.getItems().remove(removeMember);
            callback.updateLists();
        });
        this.saveButton.setOnAction(e -> {
            int i = 0;
            for (Performer member : band.getMembers()) {
                member.setPerformerName(this.BandMemberList.getItems().get(i));
                i++;
            }
            band.setPerformerName(this.performerNameTextField.getText());
            callback.updateLists();
            callback.closeStage();
        });


        this.scene = new Scene(this.popUpBorderPane);
    }

    public void checkPopularity() {
        String text = this.popularityTextField.getText();
            try {
                int value = Integer.parseInt(text);
               if(value > 100 || value < 0){
                   this.popularityTextField.setText("");
               }
            } catch (NumberFormatException e) {
                this.popularityTextField.setText("");
            }

    }
}
