package filegenerator.ast;

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
public class VariableNodeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void variableInDateSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.addVariable("test", "yyyy/mm-dd");

        AbstractAST astRoot = TestUtils.parseString("<<<date,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/mm-dd");
        String expectedResult = df.format(new Date());

        Assert.assertEquals(expectedResult, env.getOutput());
    }

    @Test(expected = FileGeneratorException.class)
    public void variableInDateAbsentVariable() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<date,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
    }
}
