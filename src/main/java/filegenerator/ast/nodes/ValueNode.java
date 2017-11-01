package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.FileGeneratorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author TheSadlig
 */
public class ValueNode extends AbstractAST {

    protected String value;
    private final static Logger LOGGER = LogManager.getLogger(ValueNode.class.getSimpleName());

    public String getValue() throws FileGeneratorException {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void execute() throws FileGeneratorException {
        LOGGER.debug("Value: " + value);
    }

}
