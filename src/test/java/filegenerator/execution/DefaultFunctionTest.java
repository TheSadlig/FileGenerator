package filegenerator.execution;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.TestUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gildas.lebel
 */
public class DefaultFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void defaultDisplayVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<default,$var,Hello World>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello World", env.getOutput());
    }

    @Test
    public void defaultDisplayVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,Hello Canada}}}<<<default,$var,Hello World>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello Canada", env.getOutput());
    }

    @Test
    public void defaultSetVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{default,$var2,$var,Hello World}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello World", env.getOutput());
    }

    @Test
    public void defaultSetVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,Hello Canada}}}{{{default,$var2,$var,Hello World}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello Canada", env.getOutput());
    }

    @Test
    public void defaultSetBothVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,Hello Canada}}}{{{set,$var2,Hello Humans}}}{{{default,$var2,$var,Hello World}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello Canada", env.getOutput());
    }

    @Test
    public void defaultSetSameVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,Hello Canada}}}{{{default,$var,$var,Hello World}}}<<<var,$var>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello Canada", env.getOutput());
    }

    @Test
    public void defaultSetSameVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,Hello Canada}}}{{{default,$var,$var,Hello World}}}<<<var,$var>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("Hello Canada", env.getOutput());
    }

}
