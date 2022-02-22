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
import javafx.stage.Stage;

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
    Button editPerformer = new Button("Edit performer");
    Button addShow = new Button("Add show");
    Agenda agenda = new Agenda();
    private String selectedPerformer;


    public MainScene(Gui gui) {

        performerList = new ListView();
        performerList.setOnMousePressed(e -> {
            selectedPerformer = performerList.getSelectionModel().getSelectedItem();
            //performerController.editPerformer(e.g);

        });
        //List components
        performerList.setOrientation(Orientation.VERTICAL);

        removePerformer.setOnAction(E -> {
            System.out.println(performerList.getSelectionModel().getSelectedItem());
            performerController.removePerformer(performerList.getSelectionModel().getSelectedItem() + "");
            performerController.updateList(performerList);
        });

        editPerformer.setOnAction(E -> {
            System.out.println("Edit button pressed");
            EditArtist editArtist = new EditArtist(selectedPerformer, gui, performerController);
            //editArtist(selectedPerformer);
        });

        addShow.setOnAction(E -> {
            System.out.println("opening");
            Stage addPerformanceStage = new Stage();
            addPerformanceStage.setScene(new AddShowScene(performerController, performerList.getSelectionModel().getSelectedItem()).getScene());
            addPerformanceStage.setResizable(false);
            addPerformanceStage.show();
        });

        TextField textField = new TextField();

        //schedule stuff
        Agenda agenda = new Agenda();
        agenda.setHeight(400);
        agenda.setWidth(800);
        agenda.drawAgendaBase();
        agenda.addShow();
        agenda.addShow();
        agenda.addShow();
        agenda.drawShows();

        agenda.setOnMousePressed(e -> agenda.mousePressed(e));
        agenda.setOnMouseReleased(e -> agenda.mouseReleased(e));
        agenda.setOnMouseDragged(e -> agenda.moveOnMouse(e.getX(), e.getY()));

        performerVBox.getChildren().addAll(performerLabel, performerList, addPerformer, addShow, editPerformer, removePerformer);
        agendaBorderPane.setRight(performerVBox);
        agendaBorderPane.setLeft(agenda);
        this.scene = new Scene(agendaBorderPane);

    }

}
