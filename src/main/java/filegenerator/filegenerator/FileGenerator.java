package filegenerator.filegenerator;

import filegenerator.FilegeneratorLexer;
import filegenerator.FilegeneratorParser;
import filegenerator.ast.AbstractAST;
import filegenerator.ast.model.Chunk;
import filegenerator.ast.printer.NodeSetPrint;
import filegenerator.ast.printer.PrinterEnvironment;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.utils.CastWizard;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import filegenerator.filegenerator.api.model.ExecutionResult;
import filegenerator.parser.FileGeneratorBaseListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author TheSadlig
 */
public class FileGenerator {

    public static void main(String[] args) throws filegenerator.execution.FileGeneratorException, IOException {
        if (args.length == 0) {
            System.out.println("Usage: java -jar FileGenerator.jar INPUT_FILE OUTPUT_FOLDER REPEATNUMBER");
            return;
        }
        FileGenerator.execute(args[0], args[1], Integer.parseInt(args[2]));
    }

    /**
     * Executes the given templates and return the ExecutionResult
     *
     * @param templateName The name of the template which will be executed
     * @param parameters the parameters of the execution
     * @return
     * @throws filegenerator.execution.FileGeneratorException
     */
    public static ExecutionResult execute(String templateName, ExecutionParameters parameters)
        throws FileGeneratorException {
        ExecutionResult result = new ExecutionResult();
        Environnement.getEnvironenement().clear();
        Environnement env = Environnement.getEnvironenement();

        env.setParameters(parameters);
        env.setResourcePath(parameters.getPath());

        for (Map.Entry<String, String> entry : parameters.getInputVariables().entrySet()) {
            env.addVariable(entry.getKey(), CastWizard.cast(entry.getValue()));
        }

        FilegeneratorLexer e = getLexer(parameters.getTemplateContent(templateName));

        Map<String, Chunk> chunks = FileGenerator.getResultingChunks(e);

        AbstractAST astRoot = FileGenerator.getASTRoot(e);
        NodeSetPrint chunkNodePrint = new NodeSetPrint(astRoot);
        chunkNodePrint.visit(null);

        PrinterEnvironment printerEnvironment = PrinterEnvironment.getInstance();
        String resultingDotFile = printerEnvironment.read();
        printerEnvironment.clear();

        result.setChunks(chunks);
        result.setResultingDotFile(resultingDotFile);
        result.setOutputVariables(env.getOutputVariables());

        env.clear();

        return result;
    }

    private static AbstractAST getASTRoot(FilegeneratorLexer e) {
        TokenStream ts = new CommonTokenStream(e);
        FilegeneratorParser parser = new FilegeneratorParser(ts);

        FileGeneratorBaseListener listener = new FileGeneratorBaseListener();
        parser.addParseListener(listener);
        parser.eval();

        AbstractAST astRoot = listener.getRoot();

        e.reset();
        return astRoot;
    }

    /**
     * Returns the executed chunks astRoot will be used to return the astRoot
     *
     * @return the list of chunks
     */
    private static Map<String, Chunk> getResultingChunks(FilegeneratorLexer e) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        TokenStream ts = new CommonTokenStream(e);
        FilegeneratorParser parser = new FilegeneratorParser(ts);

        FileGeneratorBaseListener listener = new FileGeneratorBaseListener();
        parser.addParseListener(listener);
        parser.eval();

        listener.getRoot().execute();

        e.reset();
        return env.getChunksMap();
    }

    /**
     * Returns the lexer to be used when executing
     *
     * @param inputTemplate
     * @return
     */
    public static FilegeneratorLexer getLexer(String inputTemplate) {
        ANTLRInputStream in = new ANTLRInputStream(inputTemplate);
        return new FilegeneratorLexer(in);
    }

    public static void execute(String inputFile, String outputFolder, int repetition)
        throws filegenerator.execution.FileGeneratorException, IOException {
        Environnement.getEnvironenement().clear();
        Environnement env = Environnement.getEnvironenement();
        String inputTemplate = StringUtils.readFile(inputFile);
        File f = new File(inputFile);
        env.setResourcePath(f.getAbsoluteFile().getParentFile().getAbsolutePath());

        FilegeneratorLexer e = getLexer(inputTemplate);
        for (int i = 0; i < repetition; ++i) {
            Map<String, Chunk> chunks = getResultingChunks(e);
            for (Map.Entry<String, Chunk> entry : chunks.entrySet()) {
                Chunk value = entry.getValue();
                value.writeChunk(outputFolder, i);
            }
        }
    }

}
