package filegenerator.execution.functions;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class FitLeftFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void fitLeftSingleChar() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,1,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("100000000000000", env.getOutput());
    }

    @Test
    public void fitLeftSeveralChars() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,123,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123000000000000", env.getOutput());
    }

    @Test
    public void fitLeftBiggerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,123456789123456,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123456789123456", env.getOutput());
    }
}
