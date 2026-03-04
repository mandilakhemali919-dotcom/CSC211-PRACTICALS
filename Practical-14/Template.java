 package Practical_14;

import java.util.*;
import java.util.function.Supplier;

public class TemplateFunctional {
    
    static class Pair {
        final String key;
        final String value;
        
        Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @FunctionalInterface
    interface HashTable {
        void insert(String key, String value);
        String lookup(String key);
    }

    public static void main(String[] args) {
        int m = 1_000_000;
        
        // Create data using functional approach
        Pair[] data = createShuffledData(m);
        
        int[] alphas = {75, 80, 85, 90, 95};
        int repetitions = 30;
        int queryCount = 100_000;

        printHeader();

        Arrays.stream(alphas)
                .mapToObj(alpha -> new TestConfig(alpha, (alpha * m) / 100))
                .forEach(config -> runTest(config, data, queryCount, repetitions));
    }
  

    private static Pair[] createShuffledData(int m) {
        List<Integer> keys = new ArrayList<>(m);
        for (int i = 1; i <= m; i++) keys.add(i);
        Collections.shuffle(keys);
        
        Pair[] data = new Pair[m];
        for (int i = 0; i < m; i++) {
            data[i] = new Pair(keys.get(i).toString(), Integer.toString(i + 1));
        }
        return data;
    }

    private static void runTest(TestConfig config, Pair[] allData, 
                                int queryCount, int repetitions) {
        Pair[] usedData = Arrays.copyOfRange(allData, 0, config.n());
        
        // Create and initialize tables
        HashTable openTable = createAndPopulateTable(
                () -> new openHash(allData.length), usedData);
        HashTable chainTable = createAndPopulateTable(
                () -> new chainHash(allData.length), usedData);
        
        String[] queries = generateQueries(usedData, queryCount);
        
        // Run benchmarks
        double openAvg = benchmarkTable(openTable, queries, repetitions);
        double chainAvg = benchmarkTable(chainTable, queries, repetitions);
        
        printResult(config.alphaPercent(), config.n(), openAvg, chainAvg);
    }

                

    
