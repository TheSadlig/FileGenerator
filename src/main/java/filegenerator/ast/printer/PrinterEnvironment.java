package filegenerator.ast.printer;

/**
 *
 * @author TheSadlig
 */
public class PrinterEnvironment {

    private static PrinterEnvironment instance;

    private StringBuilder dotFileHeader;
    private StringBuilder dotFileLinks;

    private Long nodeId = 0l;

    private PrinterEnvironment() {
        dotFileHeader = new StringBuilder();
        dotFileLinks = new StringBuilder();
    }

    public static PrinterEnvironment getInstance() {
        if (instance == null) {
            instance = new PrinterEnvironment();
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public Long addNode(String nodeName, Long parentNodeId) {
        dotFileHeader.append(nodeId).append(" [label=\"").append(nodeName).append("\"]; \n");

        if (parentNodeId != null) {
            dotFileLinks.append(parentNodeId).append(" -> ").append(nodeId).append(";\n");
        }

        ++nodeId;
        return nodeId - 1;
    }

    public String read() {
        return "strict digraph G { \n" + dotFileHeader.toString() + "\n\n" + dotFileLinks + " \n }";
    }
}
