package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class FunctionFitLeft implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        TypedVariable<String> typedVariable = new TypedVariable<>();
        String value = parametersList.get(0).getValue();

        long expectedSize = Long.valueOf(parametersList.get(1).getValue());
        String replacementChar = parametersList.get(2).getValue();

        while (value.length() < expectedSize) {
            value = value + replacementChar;
        }
        typedVariable.setValue(value);
        return typedVariable;
    }
}
