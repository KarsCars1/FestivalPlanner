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


public class SimulationMap {
    PerformerController performerController;
    private int width;
    private int height;
    private int tileHeight;
    private int tileWidth;
    private ArrayList<BufferedImage> tiles = new ArrayList<>();
    private int[][][] map;
    private int[][] path;
    private int layers;


    public SimulationMap(String fileName, Pathfinding pathfinding, Graphics2D graphics2D, PerformerController performerController) {
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

            for (int y = 0; y < tilemap.getHeight(); y += tileHeight) {
                for (int x = 0; x < tilemap.getWidth(); x += tileWidth) {
                    tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        layers = root.getJsonArray("layers").size();

        int j = 0;

        map = new int[layers][height][width];
        for (int i = 0; i < root.getJsonArray("layers").size(); i++) {
            //String layerType = root.getJsonArray("layers").getJsonObject(i).getJsonString("type").getString();
            String layerName = root.getJsonArray("layers").getJsonObject(i).getJsonString("name").getString();
            if (layerName.equals("Collision")) {
                System.out.println("hallo");
                boolean[][] collisions = new boolean[height][width];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        collisions[y][x] = (root.getJsonArray("layers").getJsonObject(i).getJsonArray("data").getInt(x + y * width) == 85);
                    }
                }
                pathfinding.addColisions(collisions);
                //democode
                Point point = new Point(25, 25);
                path = pathfinding.path(point);

            }
        }
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
                        System.out.println(point);

                        performerController.addLocation(pathfinding.path(point), jsonArray.getJsonObject(i1).getString("name"));
                    }
                }
                j--;
                layers--;
            }
            j++;
        }
    }

    void draw(Graphics2D g2d) {
        path = performerController.getLocations().get(0).getPath();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int i = 0; i < layers; i++) {
                    if (map[i][y][x] < 0)
                        continue;
                    if (map[i][y][x] != 0) {
                        g2d.drawImage(
                                tiles.get(map[i][y][x] - 1),
                                AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight),
                                null);
                        g2d.setColor(Color.yellow);

                    }

                }
                if (path[x][y] == 9999) {
                    g2d.drawString("X", x * tileWidth, y * tileHeight + tileHeight);
                } else if (path[x][y] == 0) {

                } else {
                    g2d.drawString(path[x][y] + "", x * tileWidth, y * tileHeight + tileHeight);
                }
            }
        }

    }
}
//credits to johan talboom