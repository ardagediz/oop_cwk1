import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Arda Gediz
 */
public class Track {

  private List<Point> points; // Field to store the Point objects

  public Track() {
    this.points = new ArrayList<>();
  }

  public Track(String filename) throws IOException{
    this(); // Calls the default constructor to initialize the points list
    readFile(filename); // Reads the file and populate the list
  }

  public void readFile(String filename) throws IOException {
    points.clear(); // Clears the list before reading the file
    Path path = Paths.get(filename);
    try (Scanner scanner = new Scanner(path)) {
      if (scanner.hasNextLine()) scanner.nextLine();

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
        add(point);
      }
    }
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
    if (points.size() < 1) throw new GPSException("Track doesn't contain enough points.");

    Point lowest = points.get(0);
    for (Point point : points) {
      if (point.getElevation() < lowest.getElevation()) {
        lowest = point;
      }
    }
    return lowest;
  }

  public Point highestPoint() {
    if (points.size() < 1) throw new GPSException("Track doesn't contain enough points.");

    Point highest = points.get(0);
    for (Point point : points) {
      if (point.getElevation() > highest.getElevation()) {
        highest = point;
      }
    }
    return highest;
  }

  public double totalDistance() {
    if (points.size() < 2) throw new GPSException("Track doesn't contain enough points to compute distance.");

    double totalDistance = 0;
    for (int i = 0; i < points.size() - 1; i++) {
      totalDistance += Point.greatCircleDistance(points.get(i), points.get(i + 1));
    }
    return totalDistance;
  }

  public double averageSpeed() {
    if (points.size() < 2) throw new GPSException("Track doesn't contain enough points to compute speed.");

    ZonedDateTime startTime = points.get(0).getTime();
    ZonedDateTime endTime = points.get(points.size() - 1).getTime();
    long totalSeconds = ChronoUnit.SECONDS.between(startTime, endTime);
    double totalMeters = totalDistance();

    if (totalSeconds == 0) throw new GPSException("Time interval is too short to compute speed.");

    return totalMeters / totalSeconds;
  }
}
