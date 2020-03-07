package filegenerator.execution;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gildas.lebel
 */
public class NestedFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void addRandValueToTest() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,50}}}{{{add,$test,(rand,0,50)}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        long longValue = Long.parseLong(env.getOutput());
        Assert.assertTrue(longValue >= 50 && longValue <= 100);
    }

    @Test
    public void addValueToVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{add,$test,(add,1,2)}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
        Assert.assertEquals("3", env.getOutput());
    }

    @Test
    public void multipleNestedFunctions() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{add,$test,(add,1,(add,1,(add,1,1)))}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
        Assert.assertEquals("4", env.getOutput());
    }

    @Test
    public void nestedFunctionsInDisplay() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<var,(add,1,(add,1,(add,1,(add,1,1))))>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
        Assert.assertEquals("5", env.getOutput());
    }
}
