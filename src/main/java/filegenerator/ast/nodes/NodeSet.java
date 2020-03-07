package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.FileGeneratorException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class NodeSet extends AbstractAST {

    protected List<AbstractAST> nodes;

    public NodeSet() {
        nodes = new ArrayList<>();
    }

    public void addNode(AbstractAST node) {
        nodes.add(node);
    }

    public AbstractAST getLastNode() {
        if (!nodes.isEmpty()) {
            return nodes.get(nodes.size() - 1);
        } else {
            return null;
        }
    }

    public List<AbstractAST> getNodeList() {
        return nodes;
    }

    @Override
    public void execute() throws FileGeneratorException {
        for (AbstractAST node : nodes) {
            node.execute();
        }
    }

}
