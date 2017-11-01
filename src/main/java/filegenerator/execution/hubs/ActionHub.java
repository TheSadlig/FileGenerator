package filegenerator.execution.hubs;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
import filegenerator.execution.functions.extended.FunctionArrayPut;
import filegenerator.execution.functions.extended.FunctionPromise;
import filegenerator.execution.utils.VariableUtils;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author TheSadlig
 */
public class ActionHub {

    private final static Logger LOGGER = LogManager.getLogger(ActionHub.class.getSimpleName());

    public static void dispatch(ParameterNode parameterNode) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String functionName = parameterNode.getNode(0).getValue();

        try {
            Function functionExecutor = getExecutor(functionName);

            ValueNode variableValueNode = parameterNode.getNode(1);
            String variableName = VariableUtils.getVariableParameter(parameterNode, 1).getVariableName();

            List<ValueNode> parametersList = parameterNode.getParametersList();
            // We remove the name of the function to call
            parametersList.remove(0);
            // And the variable name
            parametersList.remove(0);

            Function finalFunctionExecutor = (Function) functionExecutor;
            AbstractTypedVariable<?> typedVariable = finalFunctionExecutor.execute(parametersList, new ExecutionInfo(variableValueNode));

            env.addVariable(variableName, typedVariable);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.info("Cannot find a native function, looking for extended");
            switch (functionName) {
                case "put":
                    FunctionArrayPut.execute(parameterNode);
                    break;
                case "promise":
                    FunctionPromise.resolvePromise(parameterNode);
                    break;
                default:
                    throw new FileGeneratorException("Cannot find a native or extended function");
            }

        }
    }

    private static String createSpecificFunctionClassName(String functionName) {
        functionName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
        return "filegenerator.execution.functions.action.Function" + functionName;
    }

    private static String createFunctionClassName(String functionName) {
        functionName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
        return "filegenerator.execution.functions.Function" + functionName;
    }

    private static Function getExecutor(String functionName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileGeneratorException {
        Object functionExecutor = null;
        try {
            LOGGER.info("Looking for a function specific to actions: " + createSpecificFunctionClassName(functionName));
            Class functionClass = Class.forName(createSpecificFunctionClassName(functionName));

            functionExecutor = functionClass.newInstance();
        } catch (ClassNotFoundException e) {
            LOGGER.info("Looking for a generic function: " + createFunctionClassName(functionName));
            Class functionClass = Class.forName(createFunctionClassName(functionName));
            functionExecutor = functionClass.newInstance();
        }
        if (!(functionExecutor instanceof Function)) {
            throw new FileGeneratorException("There conflict with the name of the function:" + functionName);
        }
        return (Function) functionExecutor;
    }
}
