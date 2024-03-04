import java.io.IOException;

/**
 * Program to general a KML file from GPS track data stored in a file,
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Arda
 */
public class ConvertToKML {
  public static void main(String[] args) {
    if (args.length < 2) {
            System.err.println("Usage: java ConvertToKML <CSV file> <KML file>");
            System.exit(1);
    }

    try {
      Track track = new Track(args[0]);
      track.writeKML(args[1]);
      System.out.println("KML file generated: " + args[1]);
    } catch (IOException e) {
      System.err.println("Error processing file: " + e.getMessage());
      System.exit(1);
    }
  }
}
