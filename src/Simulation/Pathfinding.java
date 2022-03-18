package Simulation;

import java.awt.*;
import java.util.ArrayList;

public class Pathfinding {
    boolean[][] collisions;

    public int[][] path(Point point2D) {
        int[][] path = new int[100][100];
        ArrayList<Point> frontier = new ArrayList<>();
        frontier.add(point2D);
        int distance = 0;

        path[point2D.x][point2D.y] = distance;
        int current = 0;
        while (frontier.size()!=current) {
            int size = frontier.size();
            distance++;
            System.out.println(distance);

                System.out.println(current);
                Point currentPoint = frontier.get(current);
                current++;
                System.out.println(currentPoint.toString());
                int x = currentPoint.x;
                int y = currentPoint.y;
                System.out.println(x + "," + y);
                if (collisions[x][y]) {
                    path[x][y] = 2;
                } else if (path[x][y] == 0 && !(x == point2D.x && y == point2D.y)) {
                    path[x][y] = distance;
                }
                for (int j = -1; j <= 1; j++) {
                    for (int g = -1; g <= 1; g++) {
                        if (j + g != 0 && j - g != 0) {
                            int newX = x + j;
                            int newY = y + g;
                            System.out.println(newX + "," + newY);
                            if (newX >= 0 && newX < 100 && newY < 100 && newY >= 0) {
                                for (Point point  : frontier) {
                                    if (point.x != newX || point.y != newY){
                                        frontier.add(new Point(newX, newY));
                                    }
                                }
                            }
                        }
                    }
                }

        }
        return path;
    }

    public void addColisions(boolean[][] collisions) {
        this.collisions = collisions;
    }


}
