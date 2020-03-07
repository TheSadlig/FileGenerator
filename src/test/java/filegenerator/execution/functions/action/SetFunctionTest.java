package filegenerator.execution.functions.action;

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
public class SetFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }
    
    @Test
    public void setStringToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{set,$test,value}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("value", env.getOutput());
    }
 
}
