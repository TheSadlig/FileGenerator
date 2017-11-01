package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.DisplayNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class DisplayNodePrint extends PrinterInterface {

    public DisplayNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        DisplayNode displayNode = (DisplayNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(displayNode.getParameterNode());
        printer.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "DisplayNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return DisplayNode.class;
    }

}
