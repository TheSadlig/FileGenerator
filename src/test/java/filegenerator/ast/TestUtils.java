package filegenerator.ast;

import filegenerator.FilegeneratorLexer;
import filegenerator.FilegeneratorParser;
import filegenerator.parser.FileGeneratorBaseListener;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

/**
 *
 * @author TheSadlig
 */
public class TestUtils {

    public static AbstractAST parseString(String inputString) {
        ANTLRInputStream in = new ANTLRInputStream(inputString);
        FilegeneratorLexer e = new FilegeneratorLexer(in);
        TokenStream ts = new CommonTokenStream(e);

        FilegeneratorParser parser = new FilegeneratorParser(ts);

        List<? extends Token> tokens = e.getAllTokens();
        
        e.reset();

        FileGeneratorBaseListener listener = new FileGeneratorBaseListener();
        parser.addParseListener(listener);
        parser.eval();

        return listener.getRoot();
    }
}
