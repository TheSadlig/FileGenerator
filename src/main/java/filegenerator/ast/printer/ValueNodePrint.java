package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class ValueNodePrint extends PrinterInterface {

    public ValueNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    protected String getNodeName() throws FileGeneratorException {
        ValueNode valueNode = (ValueNode) getAbstractNode();

        if (valueNode instanceof VariableNode) {
            VariableNode variableNode = (VariableNode) valueNode;
            return "$" + variableNode.getVariableName();
        } else {
            return valueNode.getValue();
        }
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return ValueNode.class;
    }

}
