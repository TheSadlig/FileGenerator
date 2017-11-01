package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.IncludeFileNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class IncludeFileNodePrint extends PrinterInterface {

    public IncludeFileNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public void visit(Long parentNodeId) throws FileGeneratorException {
        super.visit(parentNodeId);

        IncludeFileNode includeFileNode = (IncludeFileNode) getAbstractNode();

        ParameterNodePrint printer = new ParameterNodePrint(includeFileNode.getParameterNode());
        printer.visit(getNodeId());
    }

    @Override
    protected String getNodeName() {
        return "IncludeFileNode";
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return IncludeFileNode.class;
    }

}
