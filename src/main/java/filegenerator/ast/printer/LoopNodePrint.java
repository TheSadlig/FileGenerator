package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.LoopNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class LoopNodePrint extends PrinterInterface {

    public LoopNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        LoopNode loopNode = (LoopNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(loopNode.getParameterNode());
        printer.visit(getNodeId());

        NodeSetPrint nodeSetPrint = new NodeSetPrint(loopNode);
        nodeSetPrint.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "LoopNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return LoopNode.class;
    }

}
