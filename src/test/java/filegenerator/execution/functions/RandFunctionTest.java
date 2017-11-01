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
public class RandFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void setRandTextInVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        String testInput = "{{{rand,$var,small,100}}}<<<var,$var>>>";
        AbstractAST astRoot = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();
        Assert.assertEquals(100, result.length());

        env.clear();
        env = Environnement.getEnvironenement();

        AbstractAST astRoot2 = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot2);
        astRoot2.execute();

        String result2 = env.getOutput();
        Assert.assertEquals(100, result2.length());

        // Both rand results should be different
        Assert.assertNotEquals(result, result2);
    }

    @Test
    public void setRandNumverInVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        String testInput = "{{{rand,$var,0,100000}}}<<<var,$var>>>";
        AbstractAST astRoot = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();

        long longResult = Long.parseLong(result);

        env.clear();
        env = Environnement.getEnvironenement();

        AbstractAST astRoot2 = TestUtils.parseString(testInput);
        Assert.assertNotNull(astRoot2);
        astRoot2.execute();

        String result2 = env.getOutput();

        long longResult2 = Long.parseLong(result2);

        // Both rand results should be different
        Assert.assertNotEquals(longResult, longResult2);
    }

    @Test
    public void displayRandTextSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<rand,small,100>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();
        Assert.assertEquals(100, result.length());

        env.clear();
        env = Environnement.getEnvironenement();

        AbstractAST astRoot2 = TestUtils.parseString("<<<rand,small,100>>>");
        Assert.assertNotNull(astRoot2);
        astRoot2.execute();

        String result2 = env.getOutput();
        Assert.assertEquals(100, result2.length());

        // Both rand results should be different
        Assert.assertNotEquals(result, result2);
    }

    @Test
    public void displayRandNumberSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<rand,0,100000>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();

        long longResult = Long.parseLong(result);

        env.clear();
        env = Environnement.getEnvironenement();

        AbstractAST astRoot2 = TestUtils.parseString("<<<rand,0,100000>>>");
        Assert.assertNotNull(astRoot2);
        astRoot2.execute();

        String result2 = env.getOutput();

        long longResult2 = Long.parseLong(result2);

        // Both rand results should be different
        Assert.assertNotEquals(longResult, longResult2);
    }
}
