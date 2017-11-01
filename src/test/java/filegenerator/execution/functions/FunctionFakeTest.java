package filegenerator.execution.functions;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class FunctionFakeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void setAddressInVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        String testInput = "{{{fake,$var,address}}}<<<var,$var>>>";
        AbstractAST astRoot = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();
        Assert.assertNotEquals(0, result.length());

        env.clear();
        env = Environnement.getEnvironenement();

        AbstractAST astRoot2 = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot2);
        astRoot2.execute();

        String result2 = env.getOutput();
        Assert.assertNotEquals(0, result2.length());

        // Both rand results should be different
        Assert.assertNotEquals(result, result2);
    }

}
