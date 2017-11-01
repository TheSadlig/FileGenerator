package filegenerator.ast;

import filegenerator.ast.nodes.ParameterNode;

/**
 *
 * @author TheSadlig
 */
public interface AbstractParametrizedNode {

    public ParameterNode getParameterNode();

    public void setParameterNode(ParameterNode parameterNode);
}
