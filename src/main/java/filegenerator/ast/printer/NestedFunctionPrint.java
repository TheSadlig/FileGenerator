package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.NestedFunctionNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class NestedFunctionPrint extends PrinterInterface {

    public NestedFunctionPrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        NestedFunctionNode nestedFunctionNode = (NestedFunctionNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(nestedFunctionNode.getParameterNode());
        printer.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "NestedFunctionNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return NestedFunctionNode.class;
    }

}
