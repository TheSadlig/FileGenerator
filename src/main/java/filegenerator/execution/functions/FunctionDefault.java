package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;

import java.util.List;

/**
 *
 * @author gildas.lebel
 */
public class FunctionDefault implements Function {

    public FunctionDefault() {
    }

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        TypedVariable<String> typedVariable = new TypedVariable<>();
        ValueNode nodeSource = parametersList.get(0);

        if (!(nodeSource instanceof VariableNode)) {
            throw new FileGeneratorException("The parameter `" + nodeSource.getValue() + "` needs to be a variable");
        }

        try {
            typedVariable.setValue(nodeSource.getValue());
        } catch (FileGeneratorException ex) {
            typedVariable.setValue(parametersList.get(1).getValue());
        }

        return typedVariable;
    }

}
