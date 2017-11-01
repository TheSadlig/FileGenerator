package filegenerator.execution.loop;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class LoopVariable implements LoopInterface {

    private long index;
    private final ValueNode valueNode;

    public LoopVariable(ParameterNode parameterNode) {
        valueNode = parameterNode.getNode(1);
        index = -1;
    }

    public boolean keepGoing() throws FileGeneratorException {
        try {
            long totalIterations = Long.parseLong(valueNode.getValue());

            ++index;
            return index < totalIterations;
        } catch (NumberFormatException ex) {
            throw new FileGeneratorException("The value should be an integer", ex);
        }
    }

}
