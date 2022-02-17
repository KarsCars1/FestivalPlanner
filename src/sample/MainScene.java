package sample;

import DataStructure.PerformerController;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainScene extends StandardScene {
    ListView<String> performerList = new ListView<>();
    PerformerController performerController = new PerformerController();
    //stage title and main borderpane
    BorderPane agendaBorderPane = new BorderPane();
    //Vbox components
    VBox performerVBox = new VBox();
    Label performerLabel = new Label("Performers:");
    Button addPerformer = new Button("Add performer");
    Button removePerformer = new Button("Remove performer");
    Button updatePerformer = new Button("Update performer");
    Button addPerformance = new Button("Add performance");
    Agenda agenda = new Agenda();

    public MainScene() {

        //List components
        performerList.setOrientation(Orientation.VERTICAL);
        removePerformer.setOnAction(E -> {
            System.out.println(performerList.getSelectionModel().getSelectedItem());
            performerController.removePerformer(performerList.getSelectionModel().getSelectedItem() + "");
            performerController.updateList(performerList);
        });

        TextField textField = new TextField();
        Agenda agenda = new Agenda();
        agenda.setHeight(400);
        agenda.setWidth(400);
        agenda.drawAgendaBase();
        agenda.addShow();
        agenda.drawShows();

        agenda.setOnMouseDragged(e ->
        {
            agenda.moveOnMouse(e.getX(), e.getY());
        });
        agenda.setOnMousePressed(e -> agenda.mousePressed(e));
        agenda.setOnMouseReleased(e -> agenda.mouseReleased(e));

        agenda.setOnMouseDragged(e -> {
            agenda.moveOnMouse(e.getX(), e.getY());
        });

        performerVBox.getChildren().addAll(performerLabel, performerList, addPerformer, addPerformance, updatePerformer, removePerformer);
        agendaBorderPane.setRight(performerVBox);
        agendaBorderPane.setLeft(agenda);
        this.scene = new Scene(agendaBorderPane);

    }

}
