package Planner;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class SimulationMap {
    private int width;
    private int height;

    private int tileHeight;
    private int tileWidth;

    private ArrayList<BufferedImage> tiles = new ArrayList<>();

    private int[][] map;

    public SimulationMap(String fileName) {
        JsonReader reader;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        reader = Json.createReader(stream);
        JsonObject root = reader.readObject();

        this.width = root.getInt("width");
        this.height = root.getInt("height");

        System.out.println(this.height + " " + this.width);

        //load the tilemap
        try {
            String a = root.getJsonArray("tilesets").getJsonObject(0).getString("source");

            BufferedImage tilemap = ImageIO.read(getClass().getClassLoader().getResourceAsStream(a.replace("tsx", "png")));

            tileHeight = root.getInt("tileheight");
            tileWidth = root.getInt("tilewidth");
            System.out.println(tileHeight + " " + tileWidth);

            for(int y = 0; y < tilemap.getHeight(); y += tileHeight)
            {
                for(int x = 0; x < tilemap.getWidth(); x += tileWidth)
                {
                    tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        map = new int[height][width];
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                map[y][x] = root.getJsonArray("layers").getJsonObject(0).getJsonArray("data").getInt(x+y*width);
            }
        }
    }

    void draw(Graphics2D g2d)
    {

        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if(map[y][x] < 0)
                    continue;

                g2d.drawImage(
                        tiles.get(map[y][x]-1),
                        AffineTransform.getTranslateInstance(x*tileWidth, y*tileHeight),
                        null);
            }
        }
    }
}
