package filegenerator.execution.utils;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class CastWizardTest {

    @Test
    public void stringStartingWithZero() {
        Assert.assertTrue(CastWizard.cast("0123456") instanceof String);
        Assert.assertEquals("0123456", CastWizard.cast("0123456"));
    }

    @Test
    public void convertibleToLong() {
        Assert.assertTrue(CastWizard.cast("42") instanceof Long);
        Assert.assertEquals(42l, CastWizard.cast("42"));
    }

    @Test
    public void convertibleToBigDecimal() {
        Assert.assertTrue(CastWizard.cast("42.55") instanceof BigDecimal);
        Assert.assertEquals("42.55", CastWizard.cast("42.55").toString());
    }

    @Test
    public void convertibleToString() {
        Assert.assertTrue(CastWizard.cast("Hello World") instanceof String);
        Assert.assertEquals("Hello World", CastWizard.cast("Hello World"));
    }
}
