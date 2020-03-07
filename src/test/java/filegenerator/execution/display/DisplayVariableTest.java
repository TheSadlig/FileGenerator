package filegenerator.execution.display;

import filegenerator.ast.nodes.TestUtils;
import filegenerator.ast.*;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author TheSadlig
 */
public class DisplayVariableTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void fitLeftSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,Coucou,10, >>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();
        Assert.assertEquals("Coucou    ", result);
    }
    @Test
    public void fitRightSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("<<<fitRight,Coucou,10, >>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        String result = env.getOutput();
        Assert.assertEquals("    Coucou", result);
    }

}
