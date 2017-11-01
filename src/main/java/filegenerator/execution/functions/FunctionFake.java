package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.execution.utils.FakeValueType;
import filegenerator.execution.utils.FakerWrapper;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class FunctionFake implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo)
            throws FileGeneratorException {
        if (parametersList.size() < 1) {
            throw new FileGeneratorException("The Fake function needs at least one parameter");
        }
        TypedVariable<String> fakeData = new TypedVariable<>();
        fakeData.setValue(FakerWrapper.generate(FakeValueType.valueOf(parametersList.get(0).getValue().toUpperCase())));

        return fakeData;
    }

}
