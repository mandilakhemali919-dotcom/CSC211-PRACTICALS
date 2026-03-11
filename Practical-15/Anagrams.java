import java.io.*;
import java.util.*;

public class Anagrams{

  public static void main(Strings[] args)
  {
    
        if (args.length != 1) {
            System.out.println("Usage: java Anagrams inputfile");
            System.out.println("You gave: " + Arrays.toString(args));
            System.exit(1);
        }
        
        String inputfile = args[0];
        System.out.println("Data file: " + inputfile);
        
    
        Map<String, Integer> D = new HashMap<>();
        
        try {
            // Open file with latin-1 encoding
            BufferedReader f = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputfile), "ISO-8859-1"));
            
            String line;
            int linenumber = 0;
            int lines = 0;
            
            // Read line by line (like Python's while line != "")
            while ((line = f.readLine()) != null) {
                linenumber++;
                String[] words = line.split("\\s+");

                
                for (String w : words) {
                    // Clean word: remove digits and punctuation but keep apostrophes
                    // Matches Python's: w.strip('[0123456789(,.,.;:_.!?---)]')
                    String W = w.replaceAll("[0-9(),.;:!?---]", "").trim();
                    
                    if (!W.isEmpty()) {
                        // Make lowercase
                        W = W.toLowerCase();
                        
                        // Update word count (Python's D[w] += 1)
                        if (D.containsKey(W)) {
                            D.put(W, D.get(W) + 1);
                        } else {
                            D.put(W, 1);
                        }
                    }
                }
                lines++;
            }

            f.close();
            System.out.println("Lines read: " + lines);
            System.out.println("Unique words: " + D.size());
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        
        /
          
        
        /
