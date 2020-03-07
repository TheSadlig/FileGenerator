package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.TestUtils;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gildas.lebel
 */
public class NestedFunctionPrintTest {

    @Before
    public void init() {
        Environnement.clear();
        PrinterEnvironment.clear();
    }

    @Test
    public void simpleNestedFunctionTest() throws FileGeneratorException {
        AbstractAST astRoot = TestUtils.parseString("<<<fitLeft,(rand,5,99),10,0>>>");
        Assert.assertNotNull(astRoot);

        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();
        System.out.println(resultingDotFile);

        Assert.assertTrue(resultingDotFile.contains("NodeSet"));
        Assert.assertTrue(resultingDotFile.contains("DisplayNode"));
        Assert.assertTrue(resultingDotFile.contains("ParameterNode"));
        Assert.assertTrue(resultingDotFile.contains("fitLeft"));

        Assert.assertTrue(resultingDotFile.contains("NestedFunctionNode"));
        Assert.assertTrue(resultingDotFile.contains("5"));
        Assert.assertTrue(resultingDotFile.contains("99"));
    }

}
