package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class RawTextNode extends AbstractAST {

    String rawText;

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    @Override
    public void execute() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.writeToOutput(rawText);
    }

}
