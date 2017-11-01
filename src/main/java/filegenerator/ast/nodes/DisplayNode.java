package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.DisplayHub;

/**
 *
 * @author TheSadlig
 */
public class DisplayNode extends AbstractAST implements AbstractParametrizedNode {

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

        DisplayHub.dispatch(parameterNode);
    }

}
