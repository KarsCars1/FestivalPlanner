package Simulation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Pathfinding {
    boolean[][] collisions;

    public int[][] path(Point point) {
        int[][] path = new int[100][100];
        int distance = 0;

        LinkedList<Point> frontier = new LinkedList<>();
        LinkedList<Point> currentNeighbors = new LinkedList<>();
        //LinkedList<Point> frontier = new LinkedList<>();
        frontier.add(point);
        HashMap<String, Point> reached = new HashMap<String, Point>();
        String stringPoint = String.valueOf(point.getX()) + String.valueOf(point.getY());
        reached.put(stringPoint, point);

        while (!frontier.isEmpty()) {
            currentNeighbors = frontier;
            frontier = new LinkedList<>();
            distance++;
            while (!currentNeighbors.isEmpty()) {
                Point current = currentNeighbors.remove(0);
                Point left = new Point((int) current.getX() - 1, (int) current.getY());
                Point right = new Point((int) current.getX() + 1, (int) current.getY());
                Point above = new Point((int) current.getX(), (int) current.getY() - 1);
                Point under = new Point((int) current.getX(), (int) current.getY() + 1);

                ArrayList<Point> neighbors = new ArrayList<>(Arrays.asList(left, right, above, under));
                for (Point next : neighbors) {
                    String check = String.valueOf(next.getX()) + String.valueOf(next.getY());
                    int x = (int) next.getX();
                    int y = (int) next.getY();
                    if (!reached.containsKey(check) && x >= 0 && x <= 99 && y >= 0 && y <= 99) {
                        if (collisions[y][x]) {
                            path[(int) next.getX()][(int) next.getY()] = 9999;
                        } else {
                            path[(int) next.getX()][(int) next.getY()] = distance;
                            frontier.add(next);
                            reached.put(check, next);
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
