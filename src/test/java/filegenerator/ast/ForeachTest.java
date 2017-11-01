package filegenerator.ast;

import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.model.ArrayTypedVariable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class ForeachTest {

    @Before
    public void init() {
        Environnement.getEnvironenement().clear();
    }

    @Test
    public void simpleForeachSuccess() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray(0, "value0");
        array.addToArray(1, "value1");

        env.addVariable("array", array);

        AbstractAST astRoot = TestUtils.parseString("[[[foreach,$array,$key,$value]]]<<<var,$key>>> : <<<var,$value>>>\n[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("0 : value0\n1 : value1\n", env.getOutput());
    }

    @Test(expected = FileGeneratorException.class)
    public void foreachNoArray() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();

        AbstractAST astRoot = TestUtils.parseString("[[[foreach,$array,$key,$value]]]<<<var,$key>>> : <<<var,$value>>>\n[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();
    }

    @Test
    public void foreachEmptyArray() throws filegenerator.execution.FileGeneratorException {
        filegenerator.execution.Environnement env = filegenerator.execution.Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        
        env.addVariable("array", array);

        AbstractAST astRoot = TestUtils.parseString("[[[foreach,$array,$key,$value]]]<<<var,$key>>> : <<<var,$value>>>\n[[[END]]]");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("", env.getOutput());
    }
}