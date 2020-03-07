package filegenerator.ast.nodes;

import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.SimpleHub;

/**
 *
 * @author TheSadlig
 */
public class NestedFunctionNode extends ValueNode implements AbstractParametrizedNode {

    private ParameterNode parameterNode;

    public ParameterNode getParameterNode() {
        return parameterNode;
    }

    public void setParameterNode(ParameterNode parameterNode) {
        this.parameterNode = parameterNode;
    }

    @Override
    public void execute() throws FileGeneratorException {
        parameterNode.execute();

        try {
            value = SimpleHub.dispatch(parameterNode).toString();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new FileGeneratorException("Could not find or execute nested function", ex);
        }
    }

}
