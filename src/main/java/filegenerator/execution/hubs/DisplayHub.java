package filegenerator.execution.hubs;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
import filegenerator.execution.functions.extended.FunctionPromise;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author TheSadlig
 */
public class DisplayHub {

    private final static Logger LOGGER = LogManager.getLogger(DisplayHub.class.getSimpleName());

    public static void dispatch(ParameterNode parameterNode) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        String functionName = parameterNode.getNode(0).getValue();

        try {
            Function functionExecutor = getExecutor(functionName);

            List<ValueNode> parametersList = parameterNode.getParametersList();
            // We remove the name of the function to call
            parametersList.remove(0);

            Function finalFunctionExecutor = (Function) functionExecutor;
            AbstractTypedVariable<?> result = finalFunctionExecutor.execute(parametersList, new ExecutionInfo(null));
            env.writeToOutput(result.toString());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.info("Cannot find a native function, looking for extended");
            if ("promise".equals(functionName)) {
                FunctionPromise.createPromise(parameterNode);
            } else {
                throw new FileGeneratorException("Cannot find a native or extended function");
            }
        }
    }

    private static String createSpecificFunctionClassName(String functionName) {
        functionName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
        return "filegenerator.execution.functions.display.Function" + functionName;
    }

    private static String createFunctionClassName(String functionName) {
        functionName = functionName.substring(0, 1).toUpperCase() + functionName.substring(1);
        return "filegenerator.execution.functions.Function" + functionName;
    }

    private static Function getExecutor(String functionName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileGeneratorException {
        Object functionExecutor = null;
        try {
            Class functionClass = Class.forName(createSpecificFunctionClassName(functionName));
            functionExecutor = functionClass.newInstance();
        } catch (ClassNotFoundException e) {
            Class functionClass = Class.forName(createFunctionClassName(functionName));
            functionExecutor = functionClass.newInstance();
        }
        if (!(functionExecutor instanceof Function)) {
            throw new FileGeneratorException("There conflict with the name of the function:" + functionName);
        }
        return (Function) functionExecutor;
    }
}
