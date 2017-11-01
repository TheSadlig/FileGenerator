package filegenerator.filegenerator.model;

/**
 *
 * @author TheSadlig
 */
public class TypedVariable<T> extends AbstractTypedVariable {

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
