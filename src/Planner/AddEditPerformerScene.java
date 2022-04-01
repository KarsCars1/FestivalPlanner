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

    public AddEditPerformerScene(PerformerController controller, Artist artist, GuiCallback callback) {

        popUpBorderPane = new BorderPane();
        addPerformerVBox = new VBox();
        name = new Label("Artist name:");
        performerNameTextField = new TextField();
        popularityLabel = new Label("Popularity (0 to 100)");
        popularityTextField = new TextField();
        switchToBandButton = new Button("Switch to Band");
        buttonHBox = new HBox();
        backButton = new Button("Back");
        addButton = new Button("Add to list");
        saveButton = new Button("Save Changes");

        popularityTextField.setOnKeyReleased(E -> {
            checkPopularity();
        });
        
        if (artist == null) {
            buttonHBox.getChildren().addAll(backButton, addButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, popularityLabel, popularityTextField, switchToBandButton);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        } else {
            int popularityNumber = artist.getPopularity();
            performerNameTextField.setText(artist.getPerformerName());
            popularityTextField.setText(Integer.toString(popularityNumber));
            buttonHBox.getChildren().addAll(backButton, saveButton);
            buttonHBox.setSpacing(140);
            addPerformerVBox.getChildren().addAll(name, performerNameTextField, popularityLabel, popularityTextField);
            popUpBorderPane.setTop(addPerformerVBox);
            popUpBorderPane.setBottom(buttonHBox);
        }

        backButton.setOnAction(e -> {
            callback.closeStage();
        });

        switchToBandButton.setOnAction(e -> {
            callback.setStage(new AddEditBandScene(controller, null, callback).getScene());
        });

        saveButton.setOnAction(e -> {
            artist.setPerformerName(performerNameTextField.getText());
            callback.updateLists();
            callback.closeStage();
        });

        addButton.setOnAction(e -> {
                        if (!performerNameTextField.getText().isEmpty() && !popularityTextField.getText().isEmpty()) {
                controller.addArtist(performerNameTextField.getText(), Integer.parseInt(popularityTextField.getText()));
                callback.updateLists();
                performerNameTextField.deleteText(0, performerNameTextField.getText().length());
                popularityTextField.deleteText(0, popularityTextField.getText().length());
            }
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
