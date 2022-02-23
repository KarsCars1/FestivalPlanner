package sample;

import DataStructure.Data.Show;
import DataStructure.PerformerController;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MainScene extends StandardScene {
    ListView<String> performerList = new ListView<>();
    PerformerController performerController = new PerformerController();
    //stage title and main borderpane
    BorderPane agendaBorderPane = new BorderPane();
    GridPane buttons = new GridPane();
    //Vbox components
    VBox performerVBox = new VBox();
    Label performerLabel = new Label("Performers:");
    Button addPerformer = new Button("Add performer");
    Button removePerformer = new Button("Remove performer");
    Button editPerformer = new Button("Edit performer");
    Button addShow = new Button("Add show");
    Button addLocation = new Button("Add Location");
    Button removeLocation = new Button("Remove Location");
    Button editLocation = new Button("Edit Location");
    Agenda agenda = new Agenda();
    String selectedPerformer;
    TableView showsTable = new TableView<>();
    ScrollPane agendaScroll = new ScrollPane();


    public MainScene() {

        performerList = new ListView();
        performerList.setOnMousePressed(e -> {
            selectedPerformer = performerList.getSelectionModel().getSelectedItem();
            //performerController.editPerformer(e.g);

        });
        //List components
        performerList.setOrientation(Orientation.VERTICAL);


        TextField textField = new TextField();

        //schedule stuff
        agenda.setHeight(800);
        agenda.setWidth(2600);
        agenda.drawAgendaBase();
        agenda.drawShows();

        agenda.setOnMousePressed(e -> agenda.mousePressed(e));
        agenda.setOnMouseReleased(e -> agenda.mouseReleased(e));
        agenda.setOnMouseDragged(e -> agenda.moveOnMouse(e.getX(), e.getY()));


        agendaScroll.setContent(agenda);
        agendaScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.fitToWidthProperty().setValue(false);
        agendaScroll.fitToHeightProperty().setValue(false);
        agendaScroll.setMaxHeight(600);
        agendaScroll.setMaxWidth(400);

        TableColumn showName = new TableColumn("Show");
        showName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn performer = new TableColumn("Performer");
        performer.setCellValueFactory(new PropertyValueFactory<>("performerName"));
        TableColumn beginTime = new TableColumn("Begin time");
        beginTime.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        TableColumn endTime = new TableColumn("End time");
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableColumn location = new TableColumn("Location");
        location.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        showsTable.getColumns().addAll(showName, performer, beginTime, endTime, location);

        updateShows();


        buttons.addColumn(0, addPerformer, editPerformer, removePerformer);
        buttons.addColumn(1, addShow);
        buttons.addColumn(2, addLocation, editLocation, removeLocation);
        performerVBox.getChildren().addAll(performerLabel, performerList, buttons);
        agendaBorderPane.setRight(performerVBox);

        removePerformer.setOnAction(E -> {
            System.out.println(performerList.getSelectionModel().getSelectedItem());
            performerController.removePerformer(performerList.getSelectionModel().getSelectedItem() + "");
            performerController.updateList(performerList);
        });

        HBox agendaHBox = new HBox();
        agendaHBox.getChildren().addAll(showsTable, agendaScroll);

        agendaBorderPane.setLeft(agendaHBox);
        this.scene = new Scene(agendaBorderPane);

    }

    public void updateShows() {
        ArrayList<Show> shows = performerController.getShows();
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            if (!showsTable.getItems().contains(show)) {
                showsTable.getItems().add(i, show);
                agenda.addShowBlock(show);
            }
            System.out.println("yet");


        }

    }
}
