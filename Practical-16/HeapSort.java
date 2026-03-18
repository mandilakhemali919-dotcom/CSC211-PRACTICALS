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
            
            System.out.println("Total words read: " + words.size());
            System.out.println("Unique words: " + new HashSet<>(words).size());

      
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        
        // Get unique words for sorting (like last week's dictionary keys)
        Set<String> uniqueWordSet = new HashSet<>(words);
        String[] uniqueWords = uniqueWordSet.toArray(new String[0]);
        
        System.out.println("\nSorting " + uniqueWords.length + " unique words");
        System.out.println("-" .repeat(60));
        
        // Test with small array first (20 words)
        System.out.println("\n🔍 TESTING WITH SMALL ARRAY (first 20 words):");
        String[] testArray = Arrays.copyOfRange(uniqueWords, 0, Math.min(20, uniqueWords.length));
        
        // Make copies for testing
        String[] test1 = testArray.clone();
        String[] test2 = testArray.clone();
        
        System.out.println("Original: " + Arrays.toString(testArray));
        
        // Test bottom-up heapsort
        heapSortBottomUp(test1);
        System.out.println("Bottom-up sorted: " + Arrays.toString(test1));
        
        // Test top-down heapsort
        heapSortTopDown(test2);
        System.out.println("Top-down sorted:  " + Arrays.toString(test2));
        
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("⚡ TIMING COMPARISON ON FULL DATASET:");
        
        // Make copies for timing full dataset
        String[] fullArray1 = uniqueWords.clone();
        String[] fullArray2 = uniqueWords.clone();
        
       
