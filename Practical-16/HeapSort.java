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

   
        // Time bottom-up heapsort
        startTime = System.nanoTime();
        heapSortBottomUp(fullArray1);
        endTime = System.nanoTime();
        long bottomUpTime = endTime - startTime;
        
        // Time top-down heapsort
        startTime = System.nanoTime();
        heapSortTopDown(fullArray2);
        endTime = System.nanoTime();
        long topDownTime = endTime - startTime;
        
        // Display timings
        System.out.println("\n📊 RESULTS:");
        System.out.printf("Bottom-Up Heapsort:   %10d ns (%8.3f ms)%n", 
                         bottomUpTime, bottomUpTime / 1_000_000.0);
        System.out.printf("Top-Down Heapsort:    %10d ns (%8.3f ms)%n", 
                         topDownTime, topDownTime / 1_000_000.0);
        
        double ratio = (double)topDownTime / bottomUpTime;
        System.out.printf("Ratio (Top-Down/Bottom-Up): %.2f%n", ratio);
        
        // Verify both sorts produced same result
        if (Arrays.equals(fullArray1, fullArray2)) {
            System.out.println("✅ Both sorts produced identical results!");
        } else {
            System.out.println("❌ Warning: Sorts produced different results!");
        }
        
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("📝 First 20 sorted words (bottom-up):");
        for (int i = 0; i < Math.min(20, fullArray1.length); i++) {
            System.out.printf("%2d: %s%n", i+1, fullArray1[i]);
        }
    }
    
    // ==================== HEAP UTILITY METHODS ====================
    
    /**
     * Heapify a subtree rooted at index i (for bottom-up construction)
     * n is size of heap
     */
    private static void heapify(String[] arr, int n, int i) {
        int largest = i;        // Initialize largest as root
        int left = 2 * i + 1;    // left child
        int right = 2 * i + 2;   // right child
        
        // If left child exists and is greater than root
        if (left < n && arr[left].compareTo(arr[largest]) > 0) {
            largest = left;
        }
        
        // If right child exists and is greater than current largest
        if (right < n && arr[right].compareTo(arr[largest]) > 0) {
            largest = right;
        }
        
        // If largest is not root
        if (largest != i) {
            String swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
    
    /**
     * Build heap from bottom up (approach a)
     * Starts from last non-leaf node and heapifies each node
     */
    private static void buildHeapBottomUp(String[] arr) {
        int n = arr.length;
        
        // Start from last non-leaf node and move up
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
    }
    
    
