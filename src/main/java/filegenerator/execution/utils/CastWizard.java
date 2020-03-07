package filegenerator.execution.utils;

import java.math.BigDecimal;

/**
 *
 * @author TheSadlig
 */
public class CastWizard {

    private CastWizard() {
        throw new IllegalStateException("This class is static and should never be instantiated");
    }

    public static Object cast(String element) {
        // An element starting with 0 should be kept as a string
        if (element.startsWith("0")) {
            return element;
        }
        try {
            return Long.parseLong(element);
        } catch (NumberFormatException ex) {
            // This is not a Long, we try the next thing
        }
        try {
            BigDecimal value = new BigDecimal(element);
            value = value.setScale(2);
            return value;
        } catch (NumberFormatException ex) {
            // This is not a BigDecimal, we keep it as a String
        }

        return element;
    }
}
