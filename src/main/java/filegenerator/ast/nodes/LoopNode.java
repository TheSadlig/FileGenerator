package filegenerator.ast.nodes;

import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.LoopHub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author TheSadlig
 */
public class LoopNode extends NodeSet implements AbstractParametrizedNode {

    private ParameterNode parameterNode;

    private static final Logger LOGGER = LogManager.getLogger(LoopNode.class.getSimpleName());

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
        LOGGER.debug("START LoopNode execute");
        parameterNode.execute();

        LoopHub loopHub = new LoopHub(parameterNode);
        while (loopHub.keepGoing()) {
            super.execute();
        }

        LOGGER.debug("END LoopNode execute");
    }

}
