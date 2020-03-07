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
public class DefaultFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void defaultDisplayVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<default,$var,HelloWorld>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloWorld", env.getOutput());
    }

    @Test
    public void defaultDisplayVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,HelloCanada}}}<<<default,$var,HelloWorld>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloCanada", env.getOutput());
    }

    @Test
    public void defaultSetVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{default,$var2,$var,HelloWorld}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloWorld", env.getOutput());
    }

    @Test
    public void defaultSetVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,HelloCanada}}}{{{default,$var2,$var,HelloWorld}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloCanada", env.getOutput());
    }

    @Test
    public void defaultSetBothVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,HelloCanada}}}{{{set,$var2,HelloHumans}}}{{{default,$var2,$var,HelloWorld}}}<<<var,$var2>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloCanada", env.getOutput());
    }

    @Test
    public void defaultSetSameVariableExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,HelloCanada}}}{{{default,$var,$var,HelloWorld}}}<<<var,$var>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloCanada", env.getOutput());
    }

    @Test
    public void defaultSetSameVariableNotExists() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$var,HelloCanada}}}{{{default,$var,$var,HelloWorld}}}<<<var,$var>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("HelloCanada", env.getOutput());
    }

}
