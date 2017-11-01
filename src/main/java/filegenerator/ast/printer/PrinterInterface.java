package filegenerator.ast.printer;

import filegenerator.ast.AbstractAST;
import filegenerator.ast.printer.exception.PrettyPrinterException;
import filegenerator.execution.FileGeneratorException;

public abstract class PrinterInterface {

    private AbstractAST abstractNode;

    private Long nodeId = null;

    public PrinterInterface(AbstractAST abstractNode) throws PrettyPrinterException {
        this.abstractNode = abstractNode;
        if (!isNodeValid()) {
            throw new PrettyPrinterException("The visited node is not of a correct type.\n"
                    + "Expected: " + getExpectedNodeClass() + " \n"
                    + "Given:" + abstractNode.getNodeName());
        }

    }

    public void visit(Long parentNodeId) throws FileGeneratorException {
        this.nodeId = PrinterEnvironment.getInstance().addNode(getNodeName(), parentNodeId);
    }

    protected abstract String getNodeName() throws FileGeneratorException;

    public abstract Class<?> getExpectedNodeClass();

    private boolean isNodeValid() {
        return getExpectedNodeClass().isInstance(abstractNode);
    }

    public AbstractAST getAbstractNode() {
        return abstractNode;
    }

    public Long getNodeId() {
        return nodeId;
    }

}
