package filegenerator.execution.functions.extended;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class FunctionArrayPutTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void addStringToArraySuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{put,$test,value}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Array[(0 => value)]", env.getOutput());
    }

    @Test
    public void add2StringToArraySuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{put,$test,value}}}{{{put,$test,nextValue}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Array[(0 => value)(1 => nextValue)]", env.getOutput());
    }

    @Test
    public void add2IntegerToArraySuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{put,$test,42}}}{{{put,$test,65}}}{{{put,$test,14}}}<<<var,$test>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Array[(0 => 42)(1 => 65)(2 => 14)]", env.getOutput());
    }

    @Test
    public void addSeveralIntegerToArrayOnePutForeachSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("{{{put,$myArray,50,64,81,61,84,912,99}}}[[[foreach,$myArray,$key,$value]]]<<<var,$key>>>:<<<var,$value>>> [[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("0:50 1:64 2:81 3:61 4:84 5:912 6:99 ", env.getOutput());
    }
}
