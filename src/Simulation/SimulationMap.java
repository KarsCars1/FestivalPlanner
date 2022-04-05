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

    public SimulationMap(String fileName, Pathfinding pathfinding, PerformerController performerController) {
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

            tileHeight = root.getInt("tileheight");
            tileWidth = root.getInt("tilewidth");
            ArrayList<BufferedImage> arrayList = new ArrayList<>();

            //get all tiles form the tilemap
            for (int y = 0; y < tilemap.getHeight(); y += tileHeight) {
                for (int x = 0; x < tilemap.getWidth(); x += tileWidth) {
                    arrayList.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                }
            }
            for (int i = 0; i < arrayList.size(); i++) {
                tiles[i] = arrayList.get(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        //get the tiles for each layer
        layers = root.getJsonArray("layers").size();

        int j = 0;

        map = new int[layers][height][width];

        //add collision layer
        for (int i = 0; i < root.getJsonArray("layers").size(); i++) {
            String layerName = root.getJsonArray("layers").getJsonObject(i).getJsonString("name").getString();
            if (layerName.equals("Collision")) {
                boolean[][] collisions = new boolean[height][width];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        collisions[y][x] = (root.getJsonArray("layers").getJsonObject(i).getJsonArray("data").getInt(x + y * width) == 85);
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
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        map[j][y][x] = root.getJsonArray("layers").getJsonObject(i).getJsonArray("data").getInt(x + y * width);
                    }
                }
            } else {
                if (layerType.equals("objectgroup")) {
                    JsonArray jsonArray = root.getJsonArray("layers").getJsonObject(i).getJsonArray("objects");
                    for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                        Point point = new Point(jsonArray.getJsonObject(i1).getInt("x") / 16 + (jsonArray.getJsonObject(i1).getInt("width") / 32), jsonArray.getJsonObject(i1).getInt("y") / 16 + (jsonArray.getJsonObject(i1).getInt("height") / 32));
                        Point size = new Point((jsonArray.getJsonObject(i1).getInt("width")), jsonArray.getJsonObject(i1).getInt("height"));

                        performerController.addLocation(pathfinding.path(point), jsonArray.getJsonObject(i1).getString("name"), size);
                    }
                }
                j--;
                layers--;
            }
            j++;
        }

        //create the map as an image
        mapLayer = new BufferedImage(16 * width, 16 * height, 1);
        Graphics2D graphics2D1 = mapLayer.createGraphics();
        for (int i = 0; i < layers; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (map[i][y][x] < 0)
                        continue;
                    if (map[i][y][x] != 0) {
                        graphics2D1.drawImage(tiles[(map[i][y][x] - 1)], AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight), null);
                    }
                }
            }
        }

        //sort the locations by name
        Collections.sort(performerController.getLocations());
    }

    //draw the map
    void draw(Graphics2D g2d) {
        path = performerController.getLocations().get(0).getPath();
        g2d.drawImage(mapLayer, 0, 0, null);
    }
}

