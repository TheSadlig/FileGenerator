package filegenerator.execution.functions;

import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.hubs.ExecutionInfo;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;
import java.util.List;

/**
 *
 * @author TheSadlig
 */
public class FunctionRand implements Function {

    @Override
    public AbstractTypedVariable<?> execute(List<ValueNode> parametersList, ExecutionInfo executionInfo) throws FileGeneratorException {

        String secondParameter = parametersList.get(0).getValue();

        // Is the first parameter a type of randomString ?
        if (RandomStringType.getRandomStringTypeEnum(secondParameter) == null) {
            return setRandomNumber(parametersList);
        } else {
            return setRandomString(parametersList);
        }
    }

    private TypedVariable<?> setRandomNumber(List<ValueNode> parametersList) throws FileGeneratorException {
        long lowRange = Long.parseLong(parametersList.get(0).getValue());
        long highRange = Long.parseLong(parametersList.get(1).getValue());

        long randomNum = Math.abs((Environnement.rand.nextLong() % (highRange - lowRange)) + lowRange);

        TypedVariable<Long> typedVariable = new TypedVariable<>();
        typedVariable.setValue(randomNum);
        return typedVariable;
    }

    private TypedVariable<?> setRandomString(List<ValueNode> parametersList) throws FileGeneratorException {
        String firstParameter = parametersList.get(0).getValue();
        RandomStringType stringType = RandomStringType.getRandomStringTypeEnum(firstParameter);

        long stringSize = Long.parseLong(parametersList.get(1).getValue());
        String randomString = stringType.getRandomString(stringSize);

        TypedVariable<String> typedVariable = new TypedVariable<>();
        typedVariable.setValue(randomString);
        return typedVariable;
    }
}
