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
public class FitRightFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void fitLeftSingleChar() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitRight,1,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("000000000000001", env.getOutput());
    }

    @Test
    public void fitLeftSeveralChars() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitRight,123,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("000000000000123", env.getOutput());
    }

    @Test
    public void fitLeftBiggerValue() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitRight,123456789123456,15,0>>>");
        Assert.assertNotNull(astRoot);

        astRoot.execute();

        Assert.assertEquals("123456789123456", env.getOutput());
    }
}
