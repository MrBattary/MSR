package michael.linker.msr.web.metric;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Custom counter with the thread safe map.
 *
 * @param <T> The type of object to count.
 */
public class Counter<T> {
    private static final Long FIRST_VALUE = 1L;
    private Map<T, Long> counterMap;

    public Counter() {
        counterMap = new ConcurrentHashMap<>();
    }

    /**
     * Add 1 to the provided key.
     *
     * @param key count key.
     */
    public void count(T key) {
        Long counter = counterMap.getOrDefault(key, null);
        if (counter != null) {
            counterMap.replace(key, counter + 1);
        } else {
            counterMap.put(key, FIRST_VALUE);
        }
    }

    /**
     * Retrieves values by updating the counter.
     *
     * @return copy of the results map.
     */
    public Map<T, Long> getCountedMap() {
        Map<T, Long> bufferCounterMap = counterMap;
        counterMap = new ConcurrentHashMap<>();
        return bufferCounterMap;
    }
}
