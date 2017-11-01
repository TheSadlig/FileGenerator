package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.ChunkNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class ChunkNodePrint extends PrinterInterface {

    public ChunkNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        ChunkNode chunkNode = (ChunkNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(chunkNode.getParameterNode());
        printer.visit(getNodeId());

        NodeSetPrint nodeSetPrint = new NodeSetPrint(chunkNode);
        nodeSetPrint.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "ChunkNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return ChunkNode.class;
    }

}
