package filegenerator.execution.functions.action;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.execution.utils.CastWizard;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;

/**
 * Returns a value to be set.
 *
 * @author TheSadlig
 */
public class FunctionSet implements Function {

    @Override
    public AbstractTypedVariable<Object> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        Object value = CastWizard.cast(parametersList.get(0).getValue());

        TypedVariable<Object> typedVariable = new TypedVariable<>();
        typedVariable.setValue(value);

        return typedVariable;
    }
}
