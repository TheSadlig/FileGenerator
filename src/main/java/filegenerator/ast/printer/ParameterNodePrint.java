package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class ParameterNodePrint extends PrinterInterface {

    public ParameterNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        ParameterNode parameterNode = (ParameterNode) getAbstractNode();

        List<ValueNode> valueNodes = parameterNode.getValueNodes();
        for (int i = 0; i < valueNodes.size(); ++i) {
            ValueNodePrint printer = new ValueNodePrint(valueNodes.get(i));
            printer.visit(getNodeId());
        }
    }

    @Override
    protected String getNodeName() {
        return "ParameterNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return ParameterNode.class;
    }

}
