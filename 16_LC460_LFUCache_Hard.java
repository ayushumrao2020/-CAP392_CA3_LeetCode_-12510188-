
import java.util.*;

class LFUCache {

    int capacity;
    int minFreq;

    HashMap<Integer, Integer> values;
    HashMap<Integer, Integer> frequencies;
    HashMap<Integer, LinkedHashSet<Integer>> freqMap;

    public LFUCache(int capacity) {

        this.capacity = capacity;
        this.minFreq = 0;

        values = new HashMap<>();
        frequencies = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {

        if (!values.containsKey(key)) {
            return -1;
        }

        updateFrequency(key);

        return values.get(key);
    }

    public void put(int key, int value) {

        if (capacity == 0) {
            return;
        }

        // If key already exists
        if (values.containsKey(key)) {

            values.put(key, value);
            updateFrequency(key);
            return;
        }

        // Remove LFU key if cache is full
        if (values.size() == capacity) {

            LinkedHashSet<Integer> set = freqMap.get(minFreq);

            int keyToRemove = set.iterator().next();

            set.remove(keyToRemove);

            values.remove(keyToRemove);
            frequencies.remove(keyToRemove);
        }

        // Insert new key
        values.put(key, value);
        frequencies.put(key, 1);

        freqMap.putIfAbsent(1, new LinkedHashSet<>());
        freqMap.get(1).add(key);

        minFreq = 1;
    }

    public void updateFrequency(int key) {

        int freq = frequencies.get(key);

        LinkedHashSet<Integer> set = freqMap.get(freq);
        set.remove(key);

        // If this was the last key with minimum frequency
        if (freq == minFreq && set.isEmpty()) {
            minFreq++;
        }

        // Increase frequency
        frequencies.put(key, freq + 1);

        freqMap.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqMap.get(freq + 1).add(key);
    }
}
