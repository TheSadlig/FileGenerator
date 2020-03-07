package filegenerator.execution.hubs;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.ValueNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.execution.functions.Function;
import filegenerator.execution.functions.extended.FunctionPromise;
import filegenerator.filegenerator.model.AbstractTypedVariable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This hub will handle any situation where we do not have a variable in the beginning of the parameterNode
 *
 * @author TheSadlig
 */
public class SimpleHub {

    private static final Logger LOGGER = LogManager.getLogger(SimpleHub.class.getSimpleName());

    private SimpleHub() {
        throw new IllegalStateException("This class is static and should never be instantiated");
    }

    public static AbstractTypedVariable<?> dispatch(ParameterNode parameterNode) throws FileGeneratorException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String functionName = parameterNode.getNode(0).getValue();

        Function functionExecutor = getExecutor(functionName);

        List<ValueNode> parametersList = parameterNode.getParametersList();
        // We remove the name of the function to call
        parametersList.remove(0);

        return functionExecutor.execute(parametersList, new ExecutionInfo(null));

    }

    public static void dispatchAndWrite(ParameterNode parameterNode) throws FileGeneratorException {
        String functionName = parameterNode.getNode(0).getValue();
        Environnement env = Environnement.getEnvironenement();

        try {
            env.writeToOutput(dispatch(parameterNode).toString());
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
