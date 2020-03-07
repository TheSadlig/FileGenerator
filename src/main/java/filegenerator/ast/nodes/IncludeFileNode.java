package filegenerator.ast.nodes;

import filegenerator.FilegeneratorLexer;
import filegenerator.FilegeneratorParser;
import filegenerator.ast.AbstractAST;
import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.parser.FileGeneratorBaseListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author TheSadlig
 */
public class IncludeFileNode extends NodeSet implements AbstractParametrizedNode {

    private ParameterNode parameterNode;
    private static final Logger LOGGER = LogManager.getLogger(IncludeFileNode.class.getSimpleName());

    @Override
    public ParameterNode getParameterNode() {
        return parameterNode;
    }

    @Override
    public void setParameterNode(ParameterNode parameterNode) {
        this.parameterNode = parameterNode;
    }

    @Override
    public void execute() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        parameterNode.execute();

        String fileToInject = env.getTemplateContent(parameterNode.getNode(0).getValue());
        ANTLRInputStream in = new ANTLRInputStream(fileToInject);
        FilegeneratorLexer e = new FilegeneratorLexer(in);
        TokenStream ts = new CommonTokenStream(e);
        FilegeneratorParser parser = new FilegeneratorParser(ts);
        e.reset();
        FileGeneratorBaseListener listener = new FileGeneratorBaseListener();
        parser.addParseListener(listener);
        parser.eval();
        nodes.add(listener.getRoot());

        LOGGER.debug("Include a file :");
        for (AbstractAST node : nodes) {
            node.execute();
        }
    }
}
