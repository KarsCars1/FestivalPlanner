package sample;

import DataStructure.PerformerController;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private String selectedPerformer;


    public MainScene(Gui gui, Stage stage) {

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
        addLocation.setOnAction(E->{
            Stage addLoactionStage = new Stage();
            addLoactionStage.setScene(new AddLocation(performerController).getScene());
            addLoactionStage.show();
        });

        TextField textField = new TextField();

        //schedule stuff
        Agenda agenda = new Agenda();
        agenda.setHeight(400);
        agenda.setWidth(3000);
        agenda.drawAgendaBase();
        agenda.addShow();
        agenda.addShow();
        agenda.addShow();
        agenda.drawShows();

        agenda.setOnMousePressed(e -> agenda.mousePressed(e));
        agenda.setOnMouseReleased(e -> agenda.mouseReleased(e));
        agenda.setOnMouseDragged(e -> agenda.moveOnMouse(e.getX(), e.getY()));

        ScrollPane agendaScroll = new ScrollPane();
        agendaScroll.setContent(agenda);
        agendaScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.fitToWidthProperty().setValue(false);
        agendaScroll.fitToHeightProperty().setValue(false);
        agendaScroll.setMaxHeight(600);
        agendaScroll.setMaxWidth(800);
        
        buttons.addColumn(0,addPerformer,editPerformer,removePerformer);
        buttons.addColumn(1,addShow);
        buttons.addColumn(2,addLocation,editLocation,removeLocation);
        performerVBox.getChildren().addAll(performerLabel, performerList, buttons);
        agendaBorderPane.setRight(performerVBox);
        agendaBorderPane.setLeft(agendaScroll);
        this.scene = new Scene(agendaBorderPane);

    }

}
