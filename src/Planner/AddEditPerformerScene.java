package Planner;

import DataStructure.Data.Artist;
import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//the AddEditPerformerScene is the pop up screen that appears when you want to add or edit an artist to or from the list

public class AddEditPerformerScene extends StandardScene {

    private BorderPane popUpBorderPane;
    private VBox addPerformerVBox;
    private Label name;
    private Label popularityLabel;
    private TextField performerNameTextField;
    private TextField popularityTextField;
    private Button switchToBandButton;
    private HBox buttonHBox;
    private Button backButton;
    private Button addButton;
    private Button saveButton;
    private String popuylarityString;

    public AddEditPerformerScene(PerformerController controller, Artist artist, GuiCallback callback) {

        this.popUpBorderPane = new BorderPane();
        this.addPerformerVBox = new VBox();
        this.name = new Label("Artist name:");
        this.performerNameTextField = new TextField();
        this.popularityLabel = new Label("Popularity (0 to 100)");
        this.popularityTextField = new TextField();
        this.switchToBandButton = new Button("Switch to Band");
        this.buttonHBox = new HBox();
        this.backButton = new Button("Back");
        this.addButton = new Button("Add to list");
        this.saveButton = new Button("Save Changes");

        //        this method makes sure the value that gets put into the popularity textfield is between 1 and 100
        
        popularityTextField.setOnKeyReleased(E -> {
            popuylarityString = this.popularityTextField.getText();
            checkPopularity();
        });

//      the if-statement checks if a band has been selected to edit or a new band is being added and opens up the right screen based on that

        if (artist == null) {
            this.buttonHBox.getChildren().addAll(this.backButton, this.addButton);
            this.buttonHBox.setSpacing(140);
            this.addPerformerVBox.getChildren().addAll(this.name, this.performerNameTextField, this.popularityLabel, this.popularityTextField, this.switchToBandButton);
            this.popUpBorderPane.setTop(this.addPerformerVBox);
            this.popUpBorderPane.setBottom(this.buttonHBox);
        } else {
            int popularityNumber = artist.getPopularity();
            this.performerNameTextField.setText(artist.getPerformerName());
            this.popularityTextField.setText(Integer.toString(popularityNumber));
            this.buttonHBox.getChildren().addAll(this.backButton, this.saveButton);
            this.buttonHBox.setSpacing(140);
            this.addPerformerVBox.getChildren().addAll(this.name, this.performerNameTextField, this.popularityLabel, this.popularityTextField);
            this.popUpBorderPane.setTop(this.addPerformerVBox);
            this.popUpBorderPane.setBottom(this.buttonHBox);
        }

        this.backButton.setOnAction(e -> {
            callback.closeStage();
        });

        this.switchToBandButton.setOnAction(e -> {
            callback.setStage(new AddEditBandScene(controller, null, callback).getScene());
        });

        this.saveButton.setOnAction(e -> {
            popuylarityString = this.popularityTextField.getText();
            artist.setPerformerName(this.performerNameTextField.getText());
            artist.setPopularity(Integer.parseInt(popuylarityString));
            callback.updateLists();
            callback.closeStage();
        });

        this.addButton.setOnAction(e -> {
            if (!this.performerNameTextField.getText().isEmpty() && !this.popularityTextField.getText().isEmpty()) {
                controller.addArtist(this.performerNameTextField.getText(), Integer.parseInt(this.popularityTextField.getText()));
                callback.updateLists();
                this.performerNameTextField.deleteText(0, this.performerNameTextField.getText().length());
                this.popularityTextField.deleteText(0, this.popularityTextField.getText().length());
            }
        });

        this.scene = new Scene(this.popUpBorderPane);
    }

    public void checkPopularity() {
        try {
            int value = Integer.parseInt(popuylarityString);
            if (value > 100 || value < 0) {
                this.popularityTextField.setText("");
            }
        } catch (NumberFormatException e) {
            this.popularityTextField.setText("");
        }

    }
}
