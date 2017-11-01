package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author TheSadlig
 */
public class FunctionUUID implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {
        UUID uuid = UUID.randomUUID();
        TypedVariable<String> uuidVariable = new TypedVariable<>();
        uuidVariable.setValue(uuid.toString());
        return uuidVariable;
    }

}
