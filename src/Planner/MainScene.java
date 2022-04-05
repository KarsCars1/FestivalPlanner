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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.fx.Resizable;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainScene extends StandardScene {
    private FileChooser fileChooser = new FileChooser();
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
    private Button saveButton = new Button("Save");
    private Button loadButton = new Button("Load");
    private Button startSimulation = new Button("Start Simulation");
    private Button removeShow = new Button("Remove Show");
    private Agenda agenda;
    private String selectedPerformer;
    private TableView showsTable = new TableView<>();
    private ScrollPane agendaScroll = new ScrollPane();


    public MainScene(PerformerController performerController, GuiCallback callback, SimulatorScene scene) {
        this.performerController = performerController;

        performerList = new ListView();
        performerList.setOnMousePressed(e -> {
            selectedPerformer = performerList.getSelectionModel().getSelectedItem();
        });
        //List components
        performerList.setOrientation(Orientation.VERTICAL);

        TextField textField = new TextField();

        agenda = new Agenda(performerController.getLocations());

        //set the agenda's height to the number of locations and with to the hours in a day
        agenda.setHeight(100 + performerController.getLocations().size()*100);
        agenda.setWidth(2600);
        agenda.drawAgendaBase();
        agenda.drawShows();

        //make the agenda scrollable
        agendaScroll.setContent(agenda);
        agendaScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        agendaScroll.fitToWidthProperty().setValue(false);
        agendaScroll.fitToHeightProperty().setValue(false);
        agendaScroll.setPrefSize(1000, 500);

        //make all the collumns used to display shows
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

        //add the scrollable agenda and shows table
        HBox agendaHBox = new HBox();
        agendaHBox.getChildren().addAll(showsTable, agendaScroll);
        agendaBorderPane.setLeft(agendaHBox);


        //add GUI components for saving and loading
        TextField saveTextField = new TextField("filename");
        HBox fileIOHBOX = new HBox();
        fileIOHBOX.getChildren().addAll(saveTextField , saveButton, loadButton);

        //add all buttons to the Gridpane
        buttons.addColumn(0, addPerformer, editPerformer, removePerformer);
        buttons.addColumn(1, addShow);
        buttons.addColumn(2, removeShow, startSimulation);
        performerVBox.getChildren().addAll(performerLabel, performerList, buttons, fileIOHBOX);
        agendaBorderPane.setRight(performerVBox);

        //get the selected performer and remove it, then update the change
        removePerformer.setOnAction(E -> {
            performerController.removePerformer(performerList.getSelectionModel().getSelectedItem() + "");
            performerController.updateList(performerList);
        });

        //put all components in the scene
        this.scene = new Scene(agendaBorderPane);

        //show the addPreformer scene
        addPerformer.setOnAction(E -> {
            callback.setStage(new AddEditPerformerScene(performerController, null, callback).getScene());
        });

        //show the editPerformer scene
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

        //UNUSED show addLocation scene
        addLocation.setOnAction(e -> {
            callback.setStage(new AddLocation(performerController).getScene());
        });

        //Save the current performerController to a txt file
        saveButton.setOnAction(e -> {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(saveTextField.getText()+".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(performerController);
                fileOutputStream.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //Open a filechooser to load a chosen performercontroller txt file
        loadButton.setOnAction(e -> {
            try {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(new Stage());

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                PerformerController newPerformerController = (PerformerController) ois.readObject();
                performerController.loadFrom(newPerformerController);
                callback.updateLists();
                fis.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        //show the addShow scene
        addShow.setOnAction(e -> {
            for (Artist artist : performerController.getArtists()) {
                if (artist.getPerformerName().equals(performerList.getSelectionModel().getSelectedItem())) {
                    callback.setStage(new AddShowScene(performerController, callback, artist).getScene());
                    return;
                }
            }
            callback.setStage(new AddShowScene(performerController, callback, null).getScene());
        });

        //Start the simulation 20 minutes before the first show
        startSimulation.setOnAction(e -> {
            callback.setStage(scene.getScene());
            scene.getAgendaFollower().setCurrentTime(scene.getAgendaFollower().getBeginTime());
            scene.getAnimationTimer().start();
            scene.getAgendaFollower().setRunning(true);
        });

        //Remove the selected show and update where shows are used
        removeShow.setOnAction(e -> {
            String name = "";
            for (Show show : performerController.getShows()) {
                if (showsTable.getSelectionModel().getSelectedItem().toString().contains("name='" + show.getName())){
                    name = show.getName();
                }
            }
            performerController.removeShow(performerController.getShow(name));
            callback.updateLists();
        });
    }

    //choose a txt file from the root folder
    private void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Select file");
        fileChooser.setInitialDirectory(new File("."));
        if (fileChooser.getExtensionFilters().size() == 0) {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt", "*.txt"));
        }
    }

    //updates where all the shows are displayed and used
    public void updateShows() {
        ArrayList<Show> shows = performerController.getShows();
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            if (!showsTable.getItems().contains(show)) {
                showsTable.getItems().add(i, show);
                agenda.addShowBlock(show, performerController.getLocations().indexOf(show.getLocation()));
            }
        }
        showsTable.getItems().retainAll(shows);
        agenda.getShows().clear();
        for (Show show:shows) {
            agenda.getShows().add(new ShowBlock(show, performerController.getLocations().indexOf(show.getLocation())));
        }
        agenda.update();
    }

    //updates where the performerList is used and displays
    public void updatePerformerList() {
        performerController.updateList(performerList);
    }
}
