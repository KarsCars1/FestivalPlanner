package Planner;

import DataStructure.Data.Artist;
import DataStructure.Data.Band;
import DataStructure.Data.Show;
import DataStructure.PerformerController;
import Simulation.SimulatorScene;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;

public class MainScene extends StandardScene {
    private ListView<String> performerList = new ListView<>();
    private PerformerController performerController;
    //stage title and main borderpane
    private BorderPane agendaBorderPane = new BorderPane();
    private GridPane buttons = new GridPane();
    //Vbox components
    private VBox performerVBox = new VBox();
    private Label performerLabel = new Label("Performers:");
    private Button addPerformer = new Button("Add performer");
    private Button removePerformer = new Button("Remove performer");
    private Button editPerformer = new Button("Edit performer");
    private Button addShow = new Button("Add show");
    private Button addLocation = new Button("Add Location");
    private Button removeLocation = new Button("Remove Location");
    private Button editLocation = new Button("Edit Location");
    private Button saveButton = new Button("Save");
    private Button loadButton = new Button("Load");
    private Button startSimulation = new Button("Start Simulation");
    private Agenda agenda = new Agenda();
    private String selectedPerformer;
    private TableView showsTable = new TableView<>();
    private ScrollPane agendaScroll = new ScrollPane();
    private boolean buttonToggle = true;
    public MainScene(PerformerController performerController, GuiCallback callback, SimulatorScene scene) {
        this.performerController = performerController;

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
        buttons.addColumn(1, addShow, saveButton, loadButton);
        buttons.addColumn(2, editLocation, removeLocation, startSimulation);
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

        //buttons
        editPerformer.setOnAction(E -> {
            if (!performerList.getSelectionModel().isEmpty()) {
                for (Artist artist : performerController.getArtists()) {
                    if (artist.getPerformerName().equals(performerList.getSelectionModel().getSelectedItem())) {
                        callback.setStage(new AddEditPerformerScene(performerController, artist, callback).getScene());
                        break;
                    }
                }
                for (Band band : performerController.getBands()) {
                    if (band.getPerformerName().equals(performerList.getSelectionModel().getSelectedItem())) {
                        callback.setStage(new AddEditBandScene(performerController, band, callback).getScene());
                    }
                }
            }
        });

        addPerformer.setOnAction(E -> {
            callback.setStage(new AddEditPerformerScene(performerController, null, callback).getScene());
        });

        addLocation.setOnAction(e -> {
            callback.setStage(new AddLocation(performerController).getScene());
        });

        loadButton.setOnAction(e -> {
            try {
                FileInputStream fis = new FileInputStream("Data.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                PerformerController newPerformerController = (PerformerController) ois.readObject();
                performerController.loadFrom(newPerformerController);
                fis.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        saveButton.setOnAction(e -> {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("Data.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(performerController);
                fileOutputStream.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        addShow.setOnAction(e -> {
            for (Artist artist : performerController.getArtists()) {
                if (artist.getPerformerName().equals(performerList.getSelectionModel().getSelectedItem())) {
                    callback.setStage(new AddShowScene(performerController, callback, artist).getScene());
                    return;
                }
            }
            callback.setStage(new AddShowScene(performerController, callback, null).getScene());
        });
        startSimulation.setOnAction(e -> {
            callback.setStage(scene.getScene());
        });
    }

    public void updateShows() {
        ArrayList<Show> shows = performerController.getShows();
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            if (!showsTable.getItems().contains(show)) {
                showsTable.getItems().add(i, show);
                agenda.addShowBlock(show);
            }
        }
    }

    public void updatePerformerList() {
        performerController.updateList(performerList);
    }
}
