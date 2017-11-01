package filegenerator.ast.printer;

/**
 *
 * @author TheSadlig
 */
import filegenerator.ast.AbstractAST;
import filegenerator.ast.nodes.RawTextNode;
import filegenerator.ast.printer.exception.PrettyPrinterException;

/**
 *
 * @author TheSadlig
 */
public class RawTextNodePrint extends PrinterInterface {

    public RawTextNodePrint(AbstractAST abstractNode) throws PrettyPrinterException {
        super(abstractNode);
    }

    @Override
    public Class<?> getExpectedNodeClass() {
        return RawTextNode.class;
    }

    @Override
    protected String getNodeName() {
        RawTextNode rawTextNode = (RawTextNode) getAbstractNode();
        // With graphviz, the only character to escape is the double quote
        return rawTextNode.getRawText().replaceAll("\"", "\\\"");
    }

}
