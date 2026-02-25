import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SearchBenchmark {
    
    static class Node {
        int key;
        String data;
        
        Node(int key, String data) {
            this.key = key;
            this.data = data;
        }
        
        @Override
        public String toString() {
            return key + " " + data;
        }
    }
    
    interface SearchStrategy {
        int search(Node[] array, int key);
        String getName();
    }
    
    static class LinearSearch implements SearchStrategy {
        @Override
        public int search(Node[] array, int key) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].key == key) return i;
            }
            return -1;
        }
        
        @Override
        public String getName() { return "Linear Search"; }
    }
    
    static class BinarySearch implements SearchStrategy {
        @Override
        public int search(Node[] array, int key) {
            int low = 0, high = array.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;  // Prevents overflow
                if (array[mid].key == key) return mid;
                if (array[mid].key < key) low = mid + 1;
                else high = mid - 1;
            }
            return -1;
        }
        
        @Override
        public String getName() { return "Binary Search"; }
    }
    
    static class BenchmarkResult {
        String strategyName;
        double totalTime;
        double totalTimeSquared;
        int n;
        int repetitions;
        
        BenchmarkResult(String name, int n, int reps) {
            this.strategyName = name;
            this.n = n;
            this.repetitions = reps;
        }
        
        void addMeasurement(double timeMs) {
            totalTime += timeMs;
            totalTimeSquared += timeMs * timeMs;
        }
        
        void printStats(DecimalFormat fourD, DecimalFormat fiveD) {
            double avgTime = totalTime / repetitions;
            double variance = (totalTimeSquared - repetitions * avgTime * avgTime) / (repetitions - 1);
            double stdDev = Math.sqrt(Math.max(0, variance));
            
            String border = "=".repeat(60);
            System.out.printf("\n%s\n", border);
            System.out.printf("STATISTICS: %s\n", strategyName);
            System.out.printf("%s\n", border);
            System.out.printf("Total Time        : %.3f s\n", totalTime / 1000);
            System.out.printf("Average Time      : %s s ± %s ms\n", 
                            fiveD.format(avgTime / 1000), fourD.format(stdDev));
            System.out.printf("Std Deviation     : %s ms\n", fourD.format(stdDev));
            System.out.printf("Dataset Size (n)  : %d\n", n);
            System.out.printf("Avg Time per Run  : %s µs\n", 
                            fiveD.format(avgTime / n * 1000));
            System.out.printf("Repetitions       : %d\n", repetitions);
            System.out.printf("%s\n\n", border);
        }
    }
    
    public static Node[] loadNodes(String filename) throws IOException {
        List<Node> nodes = new ArrayList<>();
        Path path = findFile(filename);
        
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.lines()
                  .map(String::trim)
                  .filter(line -> !line.isEmpty())
                  .forEach(line -> {
                      String[] parts = line.split(" ", 2);
                      if (parts.length == 2) {
                          nodes.add(new Node(Integer.parseInt(parts[0]), parts[1]));
                      }
                  });
        }
        
        return nodes.toArray(Node[]::new);
    }
    
    private static Path findFile(String filename) {
        Path path = Paths.get(filename);
        if (Files.exists(path)) return path;
        
        path = Paths.get("Practical-13", filename);
        if (Files.exists(path)) return path;
        
        throw new RuntimeException("File not found: " + filename);
    }
    
    public static int[] generateRandomKeys(int count, int maxValue) {
        return new Random().ints(count, 1, maxValue + 1).toArray();
    }
    
    public static BenchmarkResult runBenchmark(Node[] nodes, SearchStrategy strategy, 
                                               int[] keys, int repetitions) {
        BenchmarkResult result = new BenchmarkResult(strategy.getName(), nodes.length, repetitions);
        
        for (int key : keys) {
            long start = System.nanoTime();
            strategy.search(nodes, key);
            long duration = System.nanoTime() - start;
            result.addMeasurement(duration / 1_000_000.0);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        try {
            DecimalFormat fourD = new DecimalFormat("0.0000");
            DecimalFormat fiveD = new DecimalFormat("0.00000");
            
            Node[] nodes = loadNodes("ulysses.numbered");
            int repetitions = 30;
            int[] keys = generateRandomKeys(repetitions, 32654);
            
            List<SearchStrategy> strategies = Arrays.asList(
                new LinearSearch(),
                new BinarySearch()
            );
            
            System.out.println("\n🔍 SEARCH ALGORITHM BENCHMARK");
            System.out.printf("Dataset: %d records from Ulysses\n", nodes.length);
            System.out.printf("Running %d searches per algorithm...\n\n", repetitions);
            
            for (SearchStrategy strategy : strategies) {
                BenchmarkResult result = runBenchmark(nodes, strategy, keys, repetitions);
                result.printStats(fourD, fiveD);
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
