package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ActionHub;

/**
 *
 * @author TheSadlig
 */
public class ActionNode extends AbstractAST implements AbstractParametrizedNode {

    private ParameterNode parameterNode;

    public ActionNode() {
    }

    @Override
    public ParameterNode getParameterNode() {
        return parameterNode;
    }

    @Override
    public void setParameterNode(ParameterNode parameterNode) {
        this.parameterNode = parameterNode;
    }

    @Override
    public void execute() throws FileGeneratorException {
        parameterNode.execute();
        ActionHub.dispatch(parameterNode);
    }
}
