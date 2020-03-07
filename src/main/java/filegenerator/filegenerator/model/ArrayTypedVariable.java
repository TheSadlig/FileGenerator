package filegenerator.filegenerator.model;

import filegenerator.execution.FileGeneratorException;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author TheSadlig
 */
public class ArrayTypedVariable<T> extends AbstractTypedVariable<T> {

    private final SortedMap<String, T> values;

    public ArrayTypedVariable() {
        values = new TreeMap<>();
    }

    public SortedMap<String, T> getArray() {
        return values;
    }

    public void addToArray(String key, T value) {
        values.put(key, value);
    }

    public void addToArray(Integer key, T value) {
        values.put(String.valueOf(key), value);
    }

    public void addToArray(Long key, T value) {
        values.put(String.valueOf(key), value);
    }

    public T getFromArray(String key) throws FileGeneratorException {
        if (values.containsKey(key)) {
            return values.get(key);
        } else {
            throw new FileGeneratorException("Index Out of Bound Exception");
        }
    }

    public T getFromArray(Long key) throws FileGeneratorException {
        if (values.containsKey(key.toString())) {
            return values.get(key.toString());
        } else {
            throw new FileGeneratorException("Index Out of Bound Exception");
        }
    }

    public T getFromArray(Integer key) throws FileGeneratorException {
        if (values.containsKey(key.toString())) {
            return values.get(key.toString());
        } else {
            throw new FileGeneratorException("Index Out of Bound Exception");
        }
    }

    public int size() {
        return values.size();
    }

    public String toString() {
        String result = "Array[";
        result = values.entrySet().stream().map(entry -> "(" + entry.getKey() + " => " + entry.getValue() + ")").reduce(result, String::concat);
        result += "]";
        return result;
    }
}
