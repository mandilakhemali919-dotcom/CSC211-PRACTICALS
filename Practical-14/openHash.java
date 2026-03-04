

import java.util.Objects;

public class openHash {
    private static class Entry {
        String key;
        String value;
        State state = State.EMPTY;
        
        enum State { EMPTY, OCCUPIED, DELETED }
    }
    
    private final Entry[] table;
    private int size = 0;
    private final long seed;
    
    public openHash(int m) {
        if (m <= 0) throw new IllegalArgumentException("m must be positive");
        this.table = new Entry[m + 1];
        for (int i = 0; i <= m; i++) {
            table[i] = new Entry();
        }
        this.seed = mix64(System.nanoTime() ^ System.identityHashCode(this));
    }
    
    public void insert(String key, String value) {
        Objects.requireNonNull(key, "key");
        if (isFull()) throw new IllegalStateException("table is full");
        
        int base = hash(key);
        int firstDeleted = -1;
        
            
            
    


                  
    
