import java.util.*;
import java.util.function.Supplier;

public class Template{

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
