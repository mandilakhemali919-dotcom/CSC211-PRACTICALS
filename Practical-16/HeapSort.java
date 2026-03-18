import java.io.*
import java.util.*;


public class TryHeapSort{
  private static long startTime;
  private static long endTime;

public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: java TryHeapSort inputfile");
      System.out.println("You gave: " + Arrays.toStrings(args));
      System.exit(1);
    }
      String inputfile = args[0];
    System.out.println("Datafile: " + inputfile)
      System.out.println("=" + .repeat(60));

    ArrayList<String> words = new ArrayList<>();

    try{
      BufferedReader f = new BufferedReader(
        new InputStreamReader(new FileInputStream(inputfile), "ISO-8859-1"));
      String line;
        while ((line = f.readLine()) != null)
          {
            String[] parts = line.split("\\s+");
               
                for (String w : parts) {
                    // Clean word - remove punctuation but keep apostrophes
                    String cleaned = w.replaceAll("[0-9(),.;:!?---]", "").trim().toLowerCase();
                    
                    if (!cleaned.isEmpty()) {
                        words.add(cleaned);
                    }
                }
            }
            f.close();
            
            System.out.println("Total words read: " + words.siz
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        
     

            
