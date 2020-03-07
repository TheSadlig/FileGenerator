package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gildas.lebel
 */
public class DisplayNodePrintTest {

    @Before
    public void init() {
        Environnement.clear();
        PrinterEnvironment.clear();
    }

    @Test
    public void singleDisplayNodeTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<rand,0,10>>>");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("rand"));
        Assert.assertTrue(resultingDotFile.contains("0"));
        Assert.assertTrue(resultingDotFile.contains("10"));
    }

    @Test
    public void multipleDisplayNodeTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<rand,0,10>>><<<var,$test>>>");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("rand"));
        Assert.assertTrue(resultingDotFile.contains("0"));
        Assert.assertTrue(resultingDotFile.contains("10"));
        Assert.assertTrue(resultingDotFile.contains("var"));
        Assert.assertTrue(resultingDotFile.contains("$test"));
    }
}
