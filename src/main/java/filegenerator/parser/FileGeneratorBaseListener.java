package filegenerator.parser;

import filegenerator.FilegeneratorBaseListener;
import filegenerator.FilegeneratorParser;
import filegenerator.ast.AbstractAST;
import filegenerator.ast.AbstractParametrizedNode;
import filegenerator.ast.nodes.ActionNode;
import filegenerator.ast.nodes.ArrayNode;
import filegenerator.ast.nodes.ChunkNode;
import filegenerator.ast.nodes.DisplayNode;
import filegenerator.ast.nodes.IncludeFileNode;
import filegenerator.ast.nodes.LoopNode;
import filegenerator.ast.nodes.NodeSet;
import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.RawTextNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.ast.nodes.VariableNode;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Each node is responsible with handling itself : adding itself in the stack,
 * and removing itself. Also each node adds itself to its parent
 *
 * @author TheSadlig
 */
public class FileGeneratorBaseListener extends FilegeneratorBaseListener {

    private final Stack<AbstractAST> stack = new Stack<>();
    private final Stack<NodeSet> nodeSetStack = new Stack<>();
    private final static Logger LOGGER = LogManager.getLogger(FileGeneratorBaseListener.class.getSimpleName());

    private AbstractAST root;

    public AbstractAST getRoot() {
        return root;
    }

    @Override
    public void exitParameters(FilegeneratorParser.ParametersContext ctx) {
        super.exitParameters(ctx);
        stack.pop();

        LOGGER.trace("Exit Parameter");
    }

    @Override
    public void enterParameters(FilegeneratorParser.ParametersContext ctx) {
        super.enterParameters(ctx);
        LOGGER.trace("Enter Parameters: " + ctx.getText());
        AbstractAST parentNode = stack.peek();
        if (parentNode instanceof AbstractParametrizedNode) {
            ParameterNode parameterNode = new ParameterNode();
            stack.push(parameterNode);

            AbstractParametrizedNode parametrizedNode = (AbstractParametrizedNode) parentNode;
            parametrizedNode.setParameterNode(parameterNode);
        }

    }

    @Override
    public void enterValue(FilegeneratorParser.ValueContext ctx) {
        LOGGER.trace("Enter Value");
        // If the node has children, this node is a wrapper: the children will take care of this
        if (ctx.children == null || ctx.children.isEmpty()) {
            String text = ctx.start.getText();

            ValueNode value;
            if (ctx.start.getType() == FilegeneratorParser.VARIABLE) {
                VariableNode variable = new VariableNode();
                variable.setVariableName(text);
                value = variable;
            } else {
                value = new ValueNode();
                value.setValue(text);
            }

            AbstractAST subNode = stack.peek();
            if (subNode instanceof ParameterNode) {
                ((ParameterNode) subNode).addValue(value);
            } else if (subNode instanceof ArrayNode) {
                ArrayNode array = (ArrayNode) subNode;
                array.setIndexNode(value);
            } else {
                LOGGER.error("The parent should be a parametrized or array node");
            }
        }
    }

    @Override
    public void exitAction(FilegeneratorParser.ActionContext ctx) {
        super.exitAction(ctx);
        LOGGER.trace("Exit Action");
        stack.pop();
    }

    @Override
    public void enterInclude_file(FilegeneratorParser.Include_fileContext ctx) {
        LOGGER.trace("Enter Include File");
        NodeSet parentNode = nodeSetStack.peek();

        IncludeFileNode node = new IncludeFileNode();
        stack.push(node);

        parentNode.addNode(node);

    }

    @Override
    public void enterAction(FilegeneratorParser.ActionContext ctx) {
        LOGGER.trace("Enter Action");
        NodeSet parentNode = nodeSetStack.peek();

        ActionNode node = new ActionNode();
        stack.push(node);

        parentNode.addNode(node);
    }

    @Override
    public void exitEval(FilegeneratorParser.EvalContext ctx) {
        stack.pop();
        nodeSetStack.pop();

        if (stack.size() > 0) {
            LOGGER.warn("Stack not empty at the end");
        }
        if (nodeSetStack.size() > 0) {
            LOGGER.warn("Node Set Stack not empty at the end");
        }
    }

    @Override
    public void enterEval(FilegeneratorParser.EvalContext ctx) {
        LOGGER.trace("Enter Eval");
        NodeSet rootNode = new NodeSet();
        this.root = rootNode;
        stack.push(rootNode);
        nodeSetStack.push(rootNode);
    }

    @Override
    public void exitDisplay(FilegeneratorParser.DisplayContext ctx) {
        LOGGER.trace("Exit Display");
        stack.pop();
    }

    @Override
    public void enterDisplay(FilegeneratorParser.DisplayContext ctx) {
        LOGGER.trace("Enter Display");
        NodeSet parentNode = nodeSetStack.peek();

        DisplayNode node = new DisplayNode();
        stack.push(node);

        parentNode.addNode(node);
    }

    @Override
    public void exitLoop(FilegeneratorParser.LoopContext ctx) {
        LOGGER.trace("Exit Loop");
        stack.pop();
        nodeSetStack.pop();
    }

    @Override
    public void enterLoop(FilegeneratorParser.LoopContext ctx) {
        LOGGER.trace("Enter Loop");
        NodeSet parentNode = nodeSetStack.peek();

        LoopNode node = new LoopNode();
        stack.push(node);
        nodeSetStack.push(node);

        parentNode.addNode(node);
    }

    @Override
    public void enterArray(FilegeneratorParser.ArrayContext ctx) {
        LOGGER.trace("Enter Array");
        AbstractAST subNode = stack.peek();
        if (subNode instanceof ParameterNode) {
            String text = ctx.start.getText();

            ArrayNode array = new ArrayNode();
            array.setArrayName(text);

            ((ParameterNode) subNode).addValue(array);

            stack.push(array);
        } else {
            LOGGER.error("The parent of an array should be a parametrized node");
        }
    }

    @Override
    public void exitArray(FilegeneratorParser.ArrayContext ctx) {
        LOGGER.trace("Exit Array");
        AbstractAST node = stack.pop();
        if (!(node instanceof ArrayNode)) {
            LOGGER.error("Popped the wrong thing, should be an arrayNode");
        }
    }

    @Override
    public void enterRaw_text(FilegeneratorParser.Raw_textContext ctx) {
        LOGGER.trace("Enter Raw Text");
        NodeSet parentNode = nodeSetStack.peek();
        String rawText = ctx.start.getText();

        LOGGER.trace("PARENT:" + parentNode.getNodeName());
        AbstractAST previousNode = parentNode.getLastNode();
        if (previousNode != null)
            LOGGER.trace("PREVIOUS NODE:" + previousNode.getNodeName());

        if (previousNode instanceof RawTextNode) {
            LOGGER.trace("MERGE RAW");
            RawTextNode rawTextNode = (RawTextNode) previousNode;
            rawTextNode.setRawText(rawTextNode.getRawText() + rawText);
            LOGGER.trace("Merging two Raw Text nodes");
        } else {
            LOGGER.trace("ADDING RAW");
            RawTextNode textNode = new RawTextNode();
            textNode.setRawText(rawText);
            parentNode.addNode(textNode);
        }
    }

    @Override
    public void enterChunk(FilegeneratorParser.ChunkContext ctx) {
        LOGGER.trace("Enter Chunk");
        NodeSet parentNode = nodeSetStack.peek();

        ChunkNode node = new ChunkNode();
        stack.push(node);
        nodeSetStack.push(node);

        parentNode.addNode(node);
    }

    @Override
    public void exitChunk(FilegeneratorParser.ChunkContext ctx) {
        LOGGER.trace("Exit Chunk");
        stack.pop();
        nodeSetStack.pop();
    }

}
