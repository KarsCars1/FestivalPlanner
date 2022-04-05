package Planner;

import DataStructure.PerformerController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class AddLocation extends StandardScene {
    private TextField loactionNameField;


    public AddLocation(PerformerController controller) {
        GridPane pane = new GridPane();

        this.loactionNameField = new TextField();
        Button saveButton = new Button("Add Location");
        pane.add(loactionNameField, 0, 0);
        pane.add(saveButton, 0, 1);

        scene = new Scene(pane);
    }

}


