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
                
            
      
