import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Arda Gediz
 */
public class TrackInfo {
  public static void main(String[] args) {
    // Check if a filename has been provided as an argument
    if (args.length < 1) {
      System.err.println("Usage: java TrackInfo <filename>");
      return;
    }

    try {
      // Create a Track object from the filename provided as the first argument
      Track track = new Track(args[0]);

      // Print out various pieces of information about the track
      System.out.println(track.size() + " points in track");
            
      System.out.println("Lowest point is " + track.lowestPoint());
      System.out.println("Highest point is " + track.highestPoint());
            
      // Ensure the total distance is printed in kilometers with 3 decimal places
      System.out.printf("Total distance = %.3f km\n", track.totalDistance() / 1000);
            
      // Ensure the average speed is printed in meters per second with 3 decimal places
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
