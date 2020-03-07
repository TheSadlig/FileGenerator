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
public class NestedFunctionTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void addValueToVariableSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{add,$test,(rand,0,50)}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        long longValue = Long.parseLong(env.getOutput());
        Assert.assertTrue(longValue > 0 && longValue <= 50);
    }

}
