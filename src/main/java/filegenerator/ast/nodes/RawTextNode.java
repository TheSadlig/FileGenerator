package filegenerator.ast.nodes;

import filegenerator.ast.AbstractAST;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class RawTextNode extends AbstractAST {

    StringBuilder rawText;

    public String getRawText() {
        return rawText.toString();
    }

    public void setRawText(String rawText) {
        this.rawText = new StringBuilder(rawText);
    }

    public void concatenateText(String textToConcatenate) {
        rawText.append(textToConcatenate);
    }

    @Override
    public void execute() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        env.writeToOutput(rawText.toString());
    }

}
