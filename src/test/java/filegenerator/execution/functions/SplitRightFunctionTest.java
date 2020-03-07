package filegenerator.execution.functions;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gildas.lebel
 */
public class SplitRightFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void splitRightBiggerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitRight,123456789123456,5>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("12345", env.getOutput());
    }

    @Test
    public void splitRightSmallerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitRight,123,5>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123", env.getOutput());
    }

    @Test
    public void splitRightEqualValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitRight,123,3>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123", env.getOutput());
    }

    @Test
    public void splitRight1Char() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitRight,1,3>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("1", env.getOutput());
    }
}
