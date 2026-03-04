
import java.util.*;

public class chainHash {
    private final List<Map<String, String>> table;
    private final int m;
    private int size = 0;

    public chainHash(int m) {
        this.m = m;
        this.table = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            table.add(new LinkedHashMap<>()); // Preserves insertion order
    
    }

