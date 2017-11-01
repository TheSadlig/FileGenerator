package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.FileGeneratorException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class ParameterNode extends AbstractAST {

    protected List<ValueNode> values;

    public ParameterNode() {
        this.values = new ArrayList<ValueNode>();
    }

    public List<ValueNode> getValueNodes() {
        return values;
    }

    public void addValue(ValueNode value) {
        values.add(value);
    }

    public ValueNode getNode(int position) {
        return values.get(position);
    }

    public int size() {
        return values.size();
    }

    public List<ValueNode> getParametersList() {
        // We want to make a copy of the list to avoid changing the real list of the ParameterNode.
        ArrayList<ValueNode> copyOfValues = new ArrayList<>(values);
        return copyOfValues;
    }

    public static VariableNode getVariableParameter(ParameterNode parameterNode, int nodeIndex) throws FileGeneratorException {
        if (parameterNode.getNode(nodeIndex) instanceof VariableNode) {
            return (VariableNode) parameterNode.getNode(1);
        } else {
            throw new FileGeneratorException("The parameter " + nodeIndex + " should be a variable.");
        }
    }

    @Override
    public void execute() throws FileGeneratorException {
        for (ValueNode value : values) {
            value.execute();
        }
    }

}
