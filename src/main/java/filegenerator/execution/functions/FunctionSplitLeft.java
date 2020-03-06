package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;

import java.util.List;

/**
 * This function splits the input, by cutting the left part of the value
 *
 * @author gildas.lebel
 */
public class FunctionSplitLeft implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        TypedVariable<String> typedVariable = new TypedVariable<>();
        String value = parametersList.get(0).getValue();

        long expectedSize = Long.valueOf(parametersList.get(1).getValue());

        if (value.length() <= expectedSize) {
            typedVariable.setValue(value);
        } else {
            int startIndex = value.length() - (int) expectedSize;
            typedVariable.setValue(value.substring(startIndex));
        }
        return typedVariable;
    }
}
