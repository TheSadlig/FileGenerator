package filegenerator.execution.utils;

import java.math.BigDecimal;

/**
 *
 * @author TheSadlig
 */
public class CastWizard {

    public static Object cast(String element) {
        // An element starting with 0 should be kept as a string
        if (element.startsWith("0")) {
            return element;
        }
        try {
            return Long.parseLong(element);
        } catch (NumberFormatException ex) {
        }
        try {
            BigDecimal value = new BigDecimal(element);
            value = value.setScale(2);
            return value;
        } catch (NumberFormatException ex) {
        }

        return element;
    }
}
