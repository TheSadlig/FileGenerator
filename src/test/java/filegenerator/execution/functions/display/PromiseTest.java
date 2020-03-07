package filegenerator.execution.functions.display;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sadlig
 */
public class PromiseTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void setValueToVariableAfterDisplay() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<promise,$test>>>{{{set,$test,20}}}{{{promise,$test}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("20", env.getOutput());
    }

    @Test
    public void setValueTwoPromisesSameVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<promise,$test>>>-<<<promise,$test>>>{{{set,$test,50}}}{{{promise,$test}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("50-50", env.getOutput());
    }

    @Test
    public void setValueTwoVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<promise,$test>>>-<<<promise,$test>>>-<<<promise,$test2>>>-<<<promise,$test>>>{{{set,$test,50}}}{{{set,$test2,40}}}{{{promise,$test}}}{{{promise,$test2}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("50-50-40-50", env.getOutput());
    }

    @Test
    public void setValueThreeVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<promise,$test2>>>-<<<promise,$test2>>>-<<<promise,$test3>>>-<<<promise,$test>>>-<<<promise,$test2>>>-<<<promise,$test3>>>"
                + "{{{set,$test,50}}}{{{set,$test2,40}}}{{{set,$test3,25}}}{{{promise,$test}}}{{{promise,$test2}}}{{{promise,$test3}}}");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("40-40-25-50-40-25", env.getOutput());
    }
}
