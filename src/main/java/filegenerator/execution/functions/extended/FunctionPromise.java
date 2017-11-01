package filegenerator.execution.functions.extended;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.utils.VariableUtils;

/**
 *
 * @author TheSadlig
 */
public class FunctionPromise {

    public static void createPromise(ParameterNode parametersNode) throws FileGeneratorException {
        if (parametersNode.size() < 2) {
            throw new FileGeneratorException("A promise should have 1 parameters at least");
        }
        
        Environnement env = Environnement.getEnvironenement();

        VariableNode variableNode = VariableUtils.getVariableParameter(parametersNode, 1);

        env.addPromise(variableNode.getVariableName());
    }

    public static void resolvePromise(ParameterNode parametersNode) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        VariableNode variableNode = VariableUtils.getVariableParameter(parametersNode, 1);

        env.resolvePromise(variableNode.getVariableName());
    }
}
