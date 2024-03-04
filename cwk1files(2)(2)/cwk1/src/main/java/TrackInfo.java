import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Arda Gediz
 */
public class TrackInfo {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Usage: java TrackInfo <filename>");
      return;
    }

    try {
      Track track = new Track(args[0]);

      System.out.println(track.size() + " points in track");
            
      System.out.println("Lowest point is " + track.lowestPoint());
      System.out.println("Highest point is " + track.highestPoint());
      System.out.printf("Total distance = %.3f km\n", track.totalDistance() / 1000);
      System.out.printf("Average speed = %.3f m/s\n", track.averageSpeed());
    
    } catch (IOException e) {
      System.err.println("Error reading the track file: " + e.getMessage());
      System.exit(1);
    } catch (GPSException e) {
      System.err.println("Error processing the track: " + e.getMessage());
      System.exit(1);
    }
  
  }
}
