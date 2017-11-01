package filegenerator.execution.loop;

import filegenerator.ast.nodes.ParameterNode;
import filegenerator.ast.nodes.VariableNode;
import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.ArrayTypedVariable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedMap;

/**
 *
 * @author TheSadlig
 */
public class LoopForeach implements LoopInterface {

    private SortedMap<String, ?> sortedMap;
    private Queue<String> keysQueue;

    private String valueVariableName;
    private String keyVariableName;

    public LoopForeach(ParameterNode parameterNode) throws FileGeneratorException {
        if (parameterNode.size() < 4) {
            throw new FileGeneratorException("A foreach loop should have at least 4 parameters");
        }
        keysQueue = new LinkedList<>();
        extractParameters(parameterNode);
    }

    private void extractParameters(ParameterNode parameterNode) throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();

        // Getting the arrayName
        if (parameterNode.getNode(1) instanceof VariableNode) {
            VariableNode variable = (VariableNode) parameterNode.getNode(1);
            String arrayName = variable.getVariableName();
            AbstractTypedVariable abstractTypedVariable = env.getVariable(arrayName);
            
            if (abstractTypedVariable instanceof ArrayTypedVariable) {
                ArrayTypedVariable array = (ArrayTypedVariable) abstractTypedVariable;
                sortedMap = array.getArray();

                // Create a queue which will be used in the foreach
                for (String key : sortedMap.keySet()) {
                    keysQueue.add(key);
                }
            } else {
                throw new FileGeneratorException("The first parameter of a foreach loop should be an array");
            }
        } else {
            throw new FileGeneratorException("The first parameter of a foreach loop should be an array");
        }

        // Getting the name of the variable in which storing the key
        if (parameterNode.getNode(2) instanceof VariableNode) {
            VariableNode variable = (VariableNode) parameterNode.getNode(2);
            keyVariableName = variable.getVariableName();
        } else {
            throw new FileGeneratorException("The second parameter of a foreach loop should be a variable");
        }

        // Getting the name of the variable in which storing the value
        if (parameterNode.getNode(3) instanceof VariableNode) {
            VariableNode variable = (VariableNode) parameterNode.getNode(3);
            valueVariableName = variable.getVariableName();
        } else {
            throw new FileGeneratorException("The second parameter of a foreach loop should be a variable");
        }
    }

    public boolean keepGoing() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        if (keysQueue.size() > 0) {
            String keyName = keysQueue.poll();
            Object value = sortedMap.get(keyName);

            env.addVariable(keyVariableName, keyName);
            env.addVariable(valueVariableName, value);
            return true;
        } else {
            // We get out of the loop, we clear the variables
            env.removeVariable(keyVariableName);
            env.removeVariable(valueVariableName);
            return false;
        }
    }

}
