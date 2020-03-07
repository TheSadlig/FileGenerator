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
public class ChunkNodePrintTest {

    @Before
    public void init() {
        Environnement.clear();
        PrinterEnvironment.clear();
    }

    @Test
    public void noChunkTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<date,YYYYmmDD>>>");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertFalse(resultingDotFile.contains("ChunkNode"));
    }

    @Test
    public void singleChunkTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("===firstTest,PAYMENT/===<<<var,$test>>>===END===");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        // The ChunkNode is inside a NodeSet, and contains a NodeSet
        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("ChunkNode"));
        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("firstTest"));
        Assert.assertTrue(resultingDotFile.contains("PAYMENT/"));
        Assert.assertTrue(resultingDotFile.contains("DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("var"));
        Assert.assertTrue(resultingDotFile.contains("$test"));
    }

    @Test
    public void multipleChunkTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("===firstTest,PAYMENT/===<<<var,$test>>>===END=== ===secondTest,PAYMENT2/==={{{rand,$test2,0,2}}}===END===");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertEquals(3, StringUtils.countMatches(resultingDotFile, "NodeSet"));
        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "ChunkNode"));
        Assert.assertEquals(4, StringUtils.countMatches(resultingDotFile, "ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("firstTest"));
        Assert.assertTrue(resultingDotFile.contains("PAYMENT/"));
        Assert.assertTrue(resultingDotFile.contains("DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("var"));
        Assert.assertTrue(resultingDotFile.contains("$test"));
        Assert.assertTrue(resultingDotFile.contains("ActionNode"));
        Assert.assertTrue(resultingDotFile.contains("rand"));
        Assert.assertTrue(resultingDotFile.contains("$test2"));

    }
}
