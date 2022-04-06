package Simulation;

import DataStructure.PerformerController;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class SimulationMap {
    PerformerController performerController;
    private int width;
    private int height;
    private int tileHeight;
    private int tileWidth;
    private BufferedImage[] tiles = new BufferedImage[350];
    private int[][][] map;
    private int[][] path;
    private int layers;
    private boolean loaded = false;
    private BufferedImage mapLayer;

    public SimulationMap(String fileName, Pathfinding pathfinding, PerformerController performerController, Point entrance) {
        this.performerController = performerController;
        JsonReader reader;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        reader = Json.createReader(stream);
        JsonObject root = reader.readObject();
        this.width = root.getInt("width");
        this.height = root.getInt("height");

        //load the tilemap
        try {
            String a = root.getJsonArray("tilesets").getJsonObject(0).getString("source");

            BufferedImage tilemap = ImageIO.read(getClass().getClassLoader().getResourceAsStream(a.replace("tsx", "png")));

            this.tileHeight = root.getInt("tileheight");
            this.tileWidth = root.getInt("tilewidth");
            ArrayList<BufferedImage> arrayList = new ArrayList<>();

            //get all tiles form the tilemap
            for (int y = 0; y < tilemap.getHeight(); y += this.tileHeight) {
                for (int x = 0; x < tilemap.getWidth(); x += this.tileWidth) {
                    arrayList.add(tilemap.getSubimage(x, y, this.tileWidth, this.tileHeight));
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                this.tiles[i] = arrayList.get(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        //get the tiles for each layer
        this.layers = root.getJsonArray("layers").size();

        int j = 0;

        this.map = new int[this.layers][this.height][this.width];

        //add collision layer
        for (int i = 0; i < root.getJsonArray("layers").size(); i++) {
            String layerName = root.getJsonArray("layers").getJsonObject(i).getJsonString("name").getString();
            if (layerName.equals("Collision")) {
                boolean[][] collisions = new boolean[this.height][this.width];
                for (int y = 0; y < this.height; y++) {
                    for (int x = 0; x < this.width; x++) {
                        collisions[y][x] = (root.getJsonArray("layers").getJsonObject(i).getJsonArray("data").getInt(x + y * this.width) == 85);
                    }
                }
                pathfinding.addColisions(collisions);

            }
        }
        //Get teh non collision layers and objects
        for (int i = 0; i < root.getJsonArray("layers").size(); i++) {

            String layerType = root.getJsonArray("layers").getJsonObject(i).getJsonString("type").getString();

            String layerName = root.getJsonArray("layers").getJsonObject(i).getJsonString("name").getString();


            if (layerType.equals("tilelayer") && !layerName.equals("Collision")) {
                for (int y = 0; y < this.height; y++) {
                    for (int x = 0; x < this.width; x++) {
                        this.map[j][y][x] = root.getJsonArray("layers").getJsonObject(i).getJsonArray("data").getInt(x + y * this.width);
                    }
                }
            } else {
                if (layerType.equals("objectgroup")) {
                    JsonArray jsonArray = root.getJsonArray("layers").getJsonObject(i).getJsonArray("objects");
                    for (int i1 = 0; i1 < jsonArray.size(); i1++) {

                        Point point = new Point(jsonArray.getJsonObject(i1).getInt("x") / 16 + (jsonArray.getJsonObject(i1).getInt("width") / 32),
                                jsonArray.getJsonObject(i1).getInt("y") / 16 + (jsonArray.getJsonObject(i1).getInt("height") / 32));

                        Point size = new Point((jsonArray.getJsonObject(i1).getInt("width")),
                                jsonArray.getJsonObject(i1).getInt("height"));

                        performerController.addLocation(pathfinding.path(point), jsonArray.getJsonObject(i1).getString("name"), size);
                    }
                }
                j--;
                this.layers--;
            }
            j++;
        }

        //create the map as an image
        this.mapLayer = new BufferedImage(16 * this.width, 16 * this.height, 1);
        Graphics2D graphics2D1 = this.mapLayer.createGraphics();
        for (int i = 0; i < this.layers; i++) {
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    if (this.map[i][y][x] < 0)
                        continue;
                    if (this.map[i][y][x] != 0) {

                        graphics2D1.drawImage(this.tiles[(this.map[i][y][x] - 1)], AffineTransform.getTranslateInstance(x * this.tileWidth, y * this.tileHeight), null);


                    }
                }
            }
        }
        this.path = pathfinding.path(entrance);
        //sort the locations by name
        Collections.sort(performerController.getLocations());
    }
    //gets path to the entrance
    public int[][] getPath() {
        return path;
    }

    //draw the map
    void draw(Graphics2D g2d) {
        g2d.drawImage(this.mapLayer, 0, 0, null);
    }
}

