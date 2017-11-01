package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.ActionNode;
import filegenerator.ast.nodes.ChunkNode;
import filegenerator.ast.nodes.DisplayNode;
import filegenerator.ast.nodes.IncludeFileNode;
import filegenerator.ast.nodes.LoopNode;
import filegenerator.ast.nodes.NodeSet;
import filegenerator.ast.nodes.RawTextNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class NodeSetPrint extends PrinterInterface {

    public NodeSetPrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        NodeSet nodeSet = (NodeSet) getAbstractNode();
        List<AbstractAST> nodeList = nodeSet.getNodeList();
        for (AbstractAST node : nodeList) {
            PrinterInterface printer = null;

            if (ActionNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new ActionNodePrint(node);
            } else if (DisplayNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new DisplayNodePrint(node);
            } else if (LoopNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new LoopNodePrint(node);
            } else if (RawTextNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new RawTextNodePrint(node);
            } else if (ChunkNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new ChunkNodePrint(node);
            } else if (IncludeFileNode.class.getSimpleName().equals(node.getNodeName())) {
                printer = new IncludeFileNodePrint(node);
            }

            if (printer != null) {
                printer.visit(getNodeId());
            } else {
                throw new PrettyPrinterException("Impossible to find a printer for the node: " + node.getNodeName());
            }

        }
    }

    @Override
    protected String getNodeName() {
        return "NodeSet";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return NodeSet.class;
    }

}
