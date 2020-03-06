
import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
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
        String variableSource = parametersList.get(0).getValue();

        String defaultValue = parametersList.get(1).getValue();

        if (value.length() <= expectedSize) {
            typedVariable.setValue(value);
        } else {
            int startIndex = value.length() - (int) expectedSize;
            typedVariable.setValue(value.substring(startIndex));
        }
        return typedVariable;
    }

}
