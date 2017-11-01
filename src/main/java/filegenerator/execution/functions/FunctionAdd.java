package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;

/**
 * @author TheSadlig
 */
public class FunctionAdd implements Function {

    public AbstractTypedVariable<Long> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        ValueNode sourceVariable = null;
        if (executionInfo.getVariable() != null) {
            sourceVariable = executionInfo.getVariable();
        } else {
            // If we don't have a variable in executioninfo, we are not in an action
            sourceVariable = parametersList.get(0);
            parametersList.remove(0);
        }

        long sourceValue = 0l;
        try {
            sourceValue = Long.parseLong(sourceVariable.getValue());
        } catch (FileGeneratorException e) {
            // If the variable does not exist the value will be 0
        }

        long addedValue = Long.parseLong(parametersList.get(0).getValue());

        TypedVariable<Long> result = new TypedVariable<>();
        result.setValue(sourceValue + addedValue);

        return result;
    }
}
