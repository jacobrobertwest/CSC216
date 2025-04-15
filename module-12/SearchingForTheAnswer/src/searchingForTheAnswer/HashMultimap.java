package searchingForTheAnswer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class HashMultimap<K,V> {
    Map<K,List<V>> map = new HashMap<>();     // the primary map
    int total = 0;                            // total number of entries in the multimap

    /** Constructs an empty multimap. */
    public HashMultimap() { }

    /** Returns the total number of entries in the multimap. */
    public int size() { return total; }

    /** Returns whether the multimap is empty. */
    public boolean isEmpty() { return (total == 0); }

    /** Returns a (possibly empty) iteration of all values associated with the key. */
    Iterable<V> get(K key) {
        List<V> secondary = map.get(key);
        if (secondary != null)
            return secondary;
        return new ArrayList<>();             // return an empty list of values
    }

    /** Adds a new entry associating key with value. */
    void put(K key, V value) {
        List<V> secondary = map.get(key);
        if (secondary == null) {
            secondary = new ArrayList<>();
            map.put(key, secondary);          // begin using new list as secondary structure
        }
        secondary.add(value);
        total++;
    }

    /** Removes the (key,value) entry, if it exists. */
    boolean remove(K key, V value) {
        boolean wasRemoved = false;
        List<V> secondary = map.get(key);
        if (secondary != null) {
            wasRemoved = secondary.remove(value);
            if (wasRemoved) {
                total--;
                if (secondary.isEmpty())
                    map.remove(key);          // remove secondary structure from primary map
            }
        }
        return wasRemoved;
    }

    /** Removes all entries with the given key. */
    Iterable<V> removeAll(K key) {
        List<V> secondary = map.get(key);
        if (secondary != null) {
            total -= secondary.size();
            map.remove(key);
        } else
            secondary = new ArrayList<>();    // return empty list of removed values
        return secondary;
    }

    /** Returns an iteration of all entries in the multimap. */
    Iterable<Map.Entry<K,V>> entries() {
        List<Map.Entry<K,V>> result = new ArrayList<>();
        for (Map.Entry<K,List<V>> secondary : map.entrySet()) {
            K key = secondary.getKey();
            for (V value : secondary.getValue())
                result.add(new AbstractMap.SimpleEntry<K,V>(key,value));
        }
        return result;
    }
    

    public void printSorted(String outputFileName) {
        List<K> sortedKeys = new ArrayList<>(map.keySet());
        Collections.sort(sortedKeys, (o1, o2) -> o1.toString().compareTo(o2.toString()));

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (int i = 0; i < sortedKeys.size(); i++) {
            K key = sortedKeys.get(i);
            List<V> values = map.get(key);
            sb.append("    \"").append(key).append("\" : ").append(values);
            if (i < sortedKeys.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("}");

        try {
            Files.write(
                Paths.get(outputFileName),
                sb.toString().getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


}
