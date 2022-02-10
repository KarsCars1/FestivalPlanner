import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Show show1 = new Show("Show 1", new Location("Stage 1", 120, 300, 10, 10));
        Show show2 = new Show("Show 2", new Location("Stage 1", 90, 200, 10, 10));
        Show show3 = new Show("Show 3", new Location("Stage 1", 30, 100, 10, 10));
        Show show4 = new Show("Show 4", new Location("Stage 1", 90, 300, 10, 10));
        Show show5 = new Show("Show 5", new Location("Stage 1", 120, 200, 10, 10));
        Show show6 = new Show("Show 6", new Location("Stage 1", 30, 200 ,10, 10));
        Show show7 = new Show("Show 7", new Location("Stage 1", 60, 100, 10, 10));
        Show show8 = new Show("Show 8", new Location("Stage 1", 60, 300, 10, 10));

        ArrayList<Show> shows = new ArrayList<>();

        shows.add(show1);
        shows.add(show2);
        shows.add(show3);
        shows.add(show4);
        shows.add(show5);
        shows.add(show6);
        shows.add(show7);
        shows.add(show8);

        for (Show show: shows) {
            System.out.println(show.getLocation());
        }
    }
}
