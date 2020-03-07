package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.model.ArrayTypedVariable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author TheSadlig
 */
public class ArrayNodeTest {

    @Before
    public void init() {
        Environnement env = Environnement.getEnvironenement();
        env.clear();
    }

    @Test
    public void arrayInDateSuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray(0, "yyyy/mm-dd");

        env.addVariable("test", array);

        AbstractAST astRoot = TestUtils.parseString("<<<date,$test[0]>>>");
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/mm-dd");
        String expectedResult = df.format(new Date());

        Assert.assertEquals(expectedResult, env.getOutput());
    }

    @Test
    public void multipleValuesFromArraySuccess() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray(0, "This is one");
        array.addToArray(1, "This is two");
        array.addToArray(2, "This is three");

        env.addVariable("test", array);

        String input = "<<<var,$test[0],$test[1],$test[2]>>>";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("This is oneThis is twoThis is three", env.getOutput());
    }

    @Test(expected = FileGeneratorException.class)
    public void arrayIndexOutOfBound() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray(0, "This is one");
        array.addToArray(1, "This is two");
        array.addToArray(2, "This is three");

        env.addVariable("test", array);

        String input = "<<<var,$test[4]>>>";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("This is oneThis is twoThis is three", env.getOutput());
    }

    @Test
    public void arraySeenAsVariable() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray("letter0", "A");
        array.addToArray("letter1", "B");
        array.addToArray("letter2", "C");

        env.addVariable("test", array);

        String input = "<<<var,$test>>>";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("Array[(letter0 => A)(letter1 => B)(letter2 => C)]", env.getOutput());
    }

    @Test
    public void arrayVariableIndex() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        ArrayTypedVariable<String> array = new ArrayTypedVariable<String>();
        array.addToArray("letter0", "A");
        array.addToArray("letter1", "B");
        array.addToArray("letter2", "C");

        env.addVariable("test", array);
        env.addVariable("index", "letter1");

        String input = "<<<var,$test[$index]>>>";
        AbstractAST astRoot = TestUtils.parseString(input);
        Assert.assertNotNull(astRoot);
        astRoot.execute();

        Assert.assertEquals("B", env.getOutput());
    }
}
