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
 * @author TheSadlig
 */
public class AddFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void addValueToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{add,$test,25}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("25", env.getOutput());
    }

    @Test
    public void addVariableToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,25}}}{{{set,$test2,25}}}{{{add,$test2,25}}}<<<var,$test>>>-<<<var,$test2>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("25-50", env.getOutput());
    }

    @Test
    public void displayAdditionValueToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<add,$test,25>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("25", env.getOutput());
    }

    @Test
    public void displayAdditionVariableToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test2,25}}}<<<add,$test2,25>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("50", env.getOutput());
    }
}
