

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
        int firstDeleted = -1;package Practical_14;

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
        
        for (int step = 0; step < m(); step++) {
            int index = wrap(base + step);
            Entry entry = table[index];
            
            if (entry.state == Entry.State.EMPTY) {
                int put = firstDeleted != -1 ? firstDeleted : index;
                table[put].key = key;
                table[put].value = value;
                table[put].state = Entry.State.OCCUPIED;
                size++;
                return;
            }
            
            if (entry.state == Entry.State.DELETED && firstDeleted == -1) {
                firstDeleted = index;
                continue;
            }
            
            if (entry.state == Entry.State.OCCUPIED && key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        
            }
            
            if (entry.state == Entry.State.DELETED && firstDeleted == -1) {
                firstDeleted = index;
                continue;
            }
            
            if (entry.state == Entry.State.OCCUPIED && key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }
        
    
