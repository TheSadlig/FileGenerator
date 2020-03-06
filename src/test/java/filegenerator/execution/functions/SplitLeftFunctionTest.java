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
 * @author gildas.lebel
 */
public class SplitLeftFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void splitLeftBiggerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitLeft,123456789123456,5>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("23456", env.getOutput());
    }

    @Test
    public void splitLeftSmallerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitLeft,123,5>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123", env.getOutput());
    }

    @Test
    public void splitLeftEqualValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitLeft,123,3>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123", env.getOutput());
    }

    @Test
    public void splitLeft1Char() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<splitLeft,1,3>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("1", env.getOutput());
    }
}
