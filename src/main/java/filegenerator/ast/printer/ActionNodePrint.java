package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.ActionNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class ActionNodePrint extends PrinterInterface {

    public ActionNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        ActionNode actionNode = (ActionNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(actionNode.getParameterNode());
        printer.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "ActionNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return ActionNode.class;
    }

}
