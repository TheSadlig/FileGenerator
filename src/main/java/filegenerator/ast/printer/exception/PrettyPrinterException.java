package filegenerator.ast.printer.exception;

import filegenerator.execution.FileGeneratorException;

/**
 *
 * @author TheSadlig
 */
public class PrettyPrinterException extends FileGeneratorException {

    public PrettyPrinterException(String string) {
        super(string);
    }
}
