package filegenerator.ast.nodes;

import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.ast.model.Chunk;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class ChunkNode extends NodeSet implements AbstractParametrizedNode {

    private ParameterNode parameterNode;

    public ChunkNode() {}

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
        Environnement env = Environnement.getEnvironenement();

        parameterNode.execute();
        env.clearOutput();

        super.execute();

        String chunkName = parameterNode.getNode(0).getValue();
        String chunkOutputFolder = parameterNode.getNode(1).getValue();

        String chunkContent = env.getOutput();
        env.clearOutput();

        Chunk chunk = new Chunk(chunkName, chunkOutputFolder, chunkContent);
        env.storeChunk(chunk);
    }

}
