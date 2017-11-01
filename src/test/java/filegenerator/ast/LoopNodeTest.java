package filegenerator.ast;

import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class LoopNodeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void loopFromVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.addVariable("test", 10);

        AbstractAST astRoot = TestUtils.parseString("[[[for,$test]]]0[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("0000000000", env.getOutput());
    }

    @Test
    public void loopFromNumberSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("[[[for,10]]]0[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("0000000000", env.getOutput());
    }

    @Test
    public void loop0Success() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("[[[for,0]]]0[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("", env.getOutput());
    }

    @Test(expected = FileGeneratorException.class)
    public void loopOnStringFail() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("[[[for,test]]]0[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
    }
}
