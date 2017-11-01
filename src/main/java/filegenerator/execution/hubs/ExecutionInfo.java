package filegenerator.execution.hubs;

import filegenerator.ast.nodes.ValueNode;

/**
 *
 * @author TheSadlig
 */
public class ExecutionInfo {

    private final ValueNode variable;

    public ExecutionInfo(ValueNode variable) {
        this.variable = variable;
    }

    public ValueNode getVariable() {
        return variable;
    }

}
