package filegenerator.execution.functions.extended;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.utils.CastWizard;
import filegenerator.execution.utils.VariableUtils;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.ArrayTypedVariable;

/**
 *
 * @author TheSadlig
 */
public class FunctionArrayPut {

    public static void execute(ParameterNode parameterNode) throws FileGeneratorException {
        if (parameterNode.size() < 2) {
            throw new FileGeneratorException("Displaying a variable should have 2 parameters at least");
        }
        putInArray(parameterNode);
    }

    private static void putInArray(ParameterNode parameterNode) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        VariableNode variableNode = VariableUtils.getVariableParameter(parameterNode, 1);

        ArrayTypedVariable<Object> array;
        if (env.containsVariable(variableNode.getVariableName())) {
            AbstractTypedVariable<?> abstractTypedVariable = env.getVariable(variableNode.getVariableName());
            if (abstractTypedVariable instanceof ArrayTypedVariable<?>) {
                array = (ArrayTypedVariable<Object>) abstractTypedVariable;
            } else {
                throw new FileGeneratorException("The variable must be an array to add a value in it");
            }
        } else {
            array = new ArrayTypedVariable<>();
        }

        for (int i = 2; i < parameterNode.size(); ++i) {
            Object value = CastWizard.cast(parameterNode.getNode(i).getValue());
            array.addToArray(array.size(), value);
        }

        env.addVariable(variableNode.getVariableName(), array);
    }
}
