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
public class NodeSetPrintTest {

    @Before
    public void init() {
        Environnement.clear();
        PrinterEnvironment.clear();
    }

    @Test
    public void singleNodeSetTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,ThisTest,10, >>>");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("fitLeft"));
        Assert.assertTrue(resultingDotFile.contains("ThisTest"));
    }

    @Test
    public void innerNodeSetTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("[[[for,5]]]TEST[[[END]]]");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("LoopNode"));
        Assert.assertTrue(resultingDotFile.contains("ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("for"));
        Assert.assertTrue(resultingDotFile.contains("TEST"));
    }
}
