package filegenerator.ast;

import filegenerator.execution.FileGeneratorException;
import java.lang.invoke.MethodHandles;

/**
 *
 * @author TheSadlig
 */
public abstract class AbstractAST {

    public abstract void execute() throws FileGeneratorException;

    public String getNodeName() {
        return this.getClass().getSimpleName();
    }
}
