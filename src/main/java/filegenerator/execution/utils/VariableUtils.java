package filegenerator.execution.utils;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class VariableUtils {

    public static VariableNode getVariableParameter(ParameterNode parameterNode, int nodeIndex) throws FileGeneratorException {
        if (parameterNode.getNode(nodeIndex) instanceof VariableNode) {
            return (VariableNode) parameterNode.getNode(1);
        } else {
            throw new FileGeneratorException("The parameter " + nodeIndex + " should be a variable.");
        }
    }

    public static VariableNode getVariableParameter(ValueNode valueNode) throws FileGeneratorException {
        if (valueNode instanceof VariableNode) {
            return (VariableNode) valueNode;
        } else {
            throw new FileGeneratorException("The parameter should be a variable.");
        }
    }
}
