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

        this.performerList = new ListView();
        this.performerList.setOnMousePressed(e -> {
            this.selectedPerformer = this.performerList.getSelectionModel().getSelectedItem();
        });
        //List components
        this.performerList.setOrientation(Orientation.VERTICAL);

        TextField textField = new TextField();

        this.agenda = new Agenda(performerController.getLocations());

        //set the agenda's height to the number of locations and with to the hours in a day
        this.agenda.setHeight(100 + performerController.getLocations().size()*100);
        this.agenda.setWidth(2600);
        this.agenda.drawAgendaBase();
        this.agenda.drawShows();

        //make the agenda scrollable
        this.agendaScroll.setContent(agenda);
        this.agendaScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.agendaScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.agendaScroll.fitToWidthProperty().setValue(false);
        this.agendaScroll.fitToHeightProperty().setValue(false);
        this.agendaScroll.setPrefSize(1000, 500);

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
        agendaHBox.getChildren().addAll(this.showsTable, agendaScroll);
        this.agendaBorderPane.setLeft(agendaHBox);


        //add GUI components for saving and loading
        TextField saveTextField = new TextField("filename");
        HBox fileIOHBOX = new HBox();
        fileIOHBOX.getChildren().addAll(saveTextField , this.saveButton, this.loadButton);

        //add all buttons to the Gridpane
        this.buttons.addColumn(0, this.addPerformer, this.editPerformer, this.removePerformer);
        this.buttons.addColumn(1, this.addShow);
        this.buttons.addColumn(2, this.removeShow, this.startSimulation);
        this.performerVBox.getChildren().addAll(this.performerLabel, this.performerList, this.buttons, fileIOHBOX);
        this.agendaBorderPane.setRight(this.performerVBox);

        //get the selected performer and remove it, then update the change
        this.removePerformer.setOnAction(E -> {
            this.performerController.removePerformer(this.performerList.getSelectionModel().getSelectedItem() + "");
            this.performerController.updateList(this.performerList);
        });

        //put all components in the scene
        this.scene = new Scene(this.agendaBorderPane);

        //show the addPreformer scene
        this.addPerformer.setOnAction(E -> {
            callback.setStage(new AddEditPerformerScene(this.performerController, null, callback).getScene());
        });

        //show the editPerformer scene
        this.editPerformer.setOnAction(E -> {
            if (!this.performerList.getSelectionModel().isEmpty()) {
                for (Artist artist : this.performerController.getArtists()) {
                    if (artist.getPerformerName().equals(this.performerList.getSelectionModel().getSelectedItem())) {
                        callback.setStage(new AddEditPerformerScene(this.performerController, artist, callback).getScene());
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
        this.addLocation.setOnAction(e -> {
            callback.setStage(new AddLocation(this.performerController).getScene());
        });

        //Save the current performerController to a txt file
        this.saveButton.setOnAction(e -> {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(saveTextField.getText()+".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(this.performerController);
                fileOutputStream.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //Open a filechooser to load a chosen performercontroller txt file
        this.loadButton.setOnAction(e -> {
            try {
                configureFileChooser(this.fileChooser);
                File file = fileChooser.showOpenDialog(new Stage());

                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                PerformerController newPerformerController = (PerformerController) ois.readObject();
                this.performerController.loadFrom(newPerformerController);
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
        this.addShow.setOnAction(e -> {
            for (Artist artist : this.performerController.getArtists()) {
                if (artist.getPerformerName().equals(this.performerList.getSelectionModel().getSelectedItem())) {
                    callback.setStage(new AddShowScene(this.performerController, callback, artist).getScene());
                    return;
                }
            }
            callback.setStage(new AddShowScene(this.performerController, callback, null).getScene());
        });

        //Start the simulation 20 minutes before the first show
        this.startSimulation.setOnAction(e -> {
            callback.setStage(scene.getScene());
            scene.getAgendaFollower().setCurrentTime(scene.getAgendaFollower().getBeginTime());
            scene.getAnimationTimer().start();
            scene.getAgendaFollower().setRunning(true);
        });

        //Remove the selected show and update where shows are used
        this.removeShow.setOnAction(e -> {
            String name = "";
            for (Show show : this.performerController.getShows()) {
                if (this.showsTable.getSelectionModel().getSelectedItem().toString().contains("name='" + show.getName())){
                    name = show.getName();
                }
            }
            this.performerController.removeShow(this.performerController.getShow(name));
            callback.updateLists();
        });
    }

    //choose a txt file from the root folder
    private void configureFileChooser(final FileChooser FILECHOOSER) {
        FILECHOOSER.setTitle("Select file");
        FILECHOOSER.setInitialDirectory(new File("."));
        if (FILECHOOSER.getExtensionFilters().size() == 0) {
            FILECHOOSER.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt", "*.txt"));
        }
    }

    //updates where all the shows are displayed and used
    public void updateShows() {
        ArrayList<Show> shows = this.performerController.getShows();
        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            if (!this.showsTable.getItems().contains(show)) {
                this.showsTable.getItems().add(i, show);
                this.agenda.addShowBlock(show, this.performerController.getLocations().indexOf(show.getLocation()));
            }
        }
        this.showsTable.getItems().retainAll(shows);
        this.agenda.getShows().clear();
        for (Show show:shows) {
            this.agenda.getShows().add(new ShowBlock(show, this.performerController.getLocations().indexOf(show.getLocation())));
        }
        this.agenda.update();
    }

    //updates where the performerList is used and displays
    public void updatePerformerList() {
        this.performerController.updateList(this.performerList);
    }
}
