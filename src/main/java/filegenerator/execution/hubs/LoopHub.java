package filegenerator.execution.hubs;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.loop.LoopForeach;
import filegenerator.execution.loop.LoopInterface;
import filegenerator.execution.loop.LoopVariable;

/**
 *
 * @author TheSadlig
 */
public class LoopHub {

    private LoopInterface loopHandler;

    public LoopHub(ParameterNode parameterNode) throws FileGeneratorException {
        String firstParam = parameterNode.getNode(0).getValue();
        if ("for".equals(firstParam)) {
            loopHandler = new LoopVariable(parameterNode);
        } else if ("foreach".equals(firstParam)) {
            loopHandler = new LoopForeach(parameterNode);
        } else {
            throw new FileGeneratorException("This type of loop `" + firstParam + "` does not exist");
        }
    }

    /**
     * Dispatches the loop and returns a boolean to tell if the loop should
     * continue
     *
     * @return
     * @throws FileGeneratorException
     */
    public boolean keepGoing() throws FileGeneratorException {
        return loopHandler.keepGoing();
    }
}
