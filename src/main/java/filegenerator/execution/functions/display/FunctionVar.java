package filegenerator.execution.functions.display;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;

import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class FunctionVar implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        TypedVariable<String> typedVariable = new TypedVariable<>();
        StringBuilder result = new StringBuilder();
        for (ValueNode parameter : parametersList) {
            result.append(parameter.getValue());
        }
        typedVariable.setValue(result.toString());
        return typedVariable;
    }
}
