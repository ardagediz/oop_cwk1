import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Arda Gediz
 */
public class Track {

  private List<Point> points; // Field to store Point objects

  
  public Track() {
    this.points = new ArrayList<>();
  }

  public Track(String filename) throws IOException{
    this(); // Calls the default constructor to initialize the points list
    readFile(filename); // Reads the file and populate the list
  }

  // TODO: Create a stub for readFile()
  public void readFile(String filename) throws IOException {
    Path path = Paths.get(filename);
    Scanner scanner = new Scanner(path);
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] parts = line.split(",");
        if (parts.length != 4) {
          throw new GPSException("Invalid record format");
        }
        ZonedDateTime time = ZonedDateTime.parse(parts[0]);
        double longitude = Double.parseDouble(parts[1]);
        double latitude = Double.parseDouble(parts[2]);
        double elevation = Double.parseDouble(parts[3]);
        Point point = new Point(time, longitude, latitude, elevation);
        add(point); // Adds the new Point to the list
    }
    scanner.close();
    }

  public void add(Point point) {
    points.add(point);
  }

  public Point get(int index) {
    if (index < 0 || index >= points.size()) {
      throw new GPSException("Index out of bounds: " + index);
    }
    return points.get(index);
  }

  public int size() {
    return points.size();
  }

  public Point lowestPoint() {
    return null;
  }

  public Point highestPoint() {
    return null;
  }

  public double totalDistance() {
    return 0.0;
  }

  public double averageSpeed() {
    return 0.0;

  }
}
