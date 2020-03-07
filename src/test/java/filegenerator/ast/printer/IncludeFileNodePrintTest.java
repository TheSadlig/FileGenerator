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
public class IncludeFileNodePrintTest {

    @Before
    public void init() {
        Environnement.clear();
        PrinterEnvironment.clear();
    }

    @Test
    public void singleIncludeFileTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("|||MySubTpl|||");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("IncludeFileNode"));
        Assert.assertTrue(resultingDotFile.contains("MySubTpl"));
    }

    @Test
    public void multipleIncludeFileTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("|||MySubTpl||||||SubTpl2|||");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertEquals(2, StringUtils.countMatches(resultingDotFile, "IncludeFileNode"));
        Assert.assertTrue(resultingDotFile.contains("MySubTpl"));
        Assert.assertTrue(resultingDotFile.contains("SubTpl2"));
    }
}
