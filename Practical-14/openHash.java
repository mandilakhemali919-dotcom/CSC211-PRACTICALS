

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
    


                  
    
