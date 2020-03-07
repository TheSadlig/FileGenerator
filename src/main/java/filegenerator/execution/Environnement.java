package filegenerator.execution;

import filegenerator.ast.model.Chunk;
import filegenerator.filegenerator.StringUtils;
import filegenerator.filegenerator.api.model.ExecutionParameters;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.TypedVariable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author TheSadlig
 */
public class Environnement {

    private static Environnement instance;

    private StringBuilder outputString;

    private final SortedMap<String, AbstractTypedVariable> variableMap;

    private final TreeMap<String, Chunk> chunksMap;

    private String resourcePath;

    private ExecutionParameters parameters;

    public final static Random rand = new Random();

    // This map will contain the variable name, and the position of the promises
    private SortedMap<String, List<Integer>> promisesMap;

    // When resolving promises, every other promise needs to be shifted to have the correct position;
    private Environnement() {
        variableMap = new TreeMap<>();
        outputString = new StringBuilder();
        chunksMap = new TreeMap<>();
        promisesMap = new TreeMap<>();
        parameters = new ExecutionParameters();
    }

    public static Environnement getEnvironenement() {
        if (instance == null) {
            instance = new Environnement();
        }
        return instance;
    }

    public void writeToOutput(String strToAppend) {
        outputString.append(strToAppend);
    }

    public String getOutput() {
        return outputString.toString();
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void addVariable(String variableName, AbstractTypedVariable typedVariable) {
        variableMap.put(variableName, typedVariable);
    }

    public AbstractTypedVariable getVariable(String variableName) {
        return variableMap.get(variableName);
    }

    public void addPromise(String variableName) {
        if (promisesMap.containsKey(variableName)) {
            promisesMap.get(variableName).add(outputString.length());
        } else {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(outputString.length());
            promisesMap.put(variableName, list);
        }
    }

    public void resolvePromise(String variableName) throws FileGeneratorException {
        if (promisesMap.containsKey(variableName)) {
            List<Integer> positions = promisesMap.get(variableName);
            Integer shift = 0;
            for (Integer position : positions) {
                String variableContent = getVariable(variableName).toString();

                outputString.insert(position + shift, variableContent);
                updatePromisesPositions(variableContent.length(), position + shift, variableName);
                shift += variableContent.length();
            }
        } else {
            throw new FileGeneratorException("The required promise does not exist");
        }
    }

    private void updatePromisesPositions(Integer shiftSize, Integer resolvedPosition, String variableName) {
        TreeMap<String, List<Integer>> newPromisesMap = new TreeMap<>();

        for (Map.Entry<String, List<Integer>> promise : promisesMap.entrySet()) {
            if (!promise.getKey().equals(variableName)) {
                List<Integer> newPositionList = new ArrayList<>();
                for (Integer promisePosition : promise.getValue()) {
                    if (promisePosition >= resolvedPosition) {
                        newPositionList.add(promisePosition + shiftSize);
                    } else {
                        newPositionList.add(promisePosition);
                    }
                }
                newPromisesMap.put(promise.getKey(), newPositionList);
            }
        }
        promisesMap = newPromisesMap;
    }

    public Boolean containsVariable(String variableName) {
        return variableMap.containsKey(variableName);
    }

    public void removeVariable(String variableName) {
        variableMap.remove(variableName);
    }

    public void addVariable(String variableName, Object value) throws FileGeneratorException {
        if (value instanceof String) {
            TypedVariable<String> var = new TypedVariable<>();
            var.setValue((String) value);
            variableMap.put(variableName, var);
        } else if (value instanceof BigDecimal) {
            TypedVariable<BigDecimal> var = new TypedVariable<>();
            var.setValue((BigDecimal) value);
            variableMap.put(variableName, var);
        } else if (value instanceof Long) {
            TypedVariable<Long> var = new TypedVariable<>();
            var.setValue((Long) value);
            variableMap.put(variableName, var);
        } else if (value instanceof Integer) {
            TypedVariable<Integer> var = new TypedVariable<>();
            var.setValue((Integer) value);
            variableMap.put(variableName, var);
        } else {
            throw new FileGeneratorException(
                "Impossible to store the variable `" + variableName + "` due to impossible casting");
        }
    }

    public Map<String, Chunk> getChunksMap() {
        return chunksMap;
    }

    public void storeChunk(Chunk chunk) {
        chunksMap.put(chunk.getChunkName(), chunk);
    }

    public void clearOutput() {
        outputString = new StringBuilder();
    }

    public void clear() {
        instance = null;
    }

    public ExecutionParameters getParameters() {
        return parameters;
    }

    public void setParameters(ExecutionParameters parameters) {
        this.parameters = parameters;
    }

    public String getTemplateContent(String templateName) throws FileGeneratorException {
        String content = parameters.getTemplateContent(templateName);
        if (content == null) {
            try {
                content = StringUtils.readFile(getResourcePath() + "/" + templateName);
            } catch (IOException ex) {
                throw new FileGeneratorException("Cannot find the template: " + templateName, ex);
            }
            parameters.addTemplatesContent(templateName, content);
        }
        return content;
    }

    public HashMap<String, AbstractTypedVariable> getOutputVariables() {
        List<String> variableNames = parameters.getExpectedVariables();
        HashMap<String, AbstractTypedVariable> outputVariables = new HashMap<>();
        for (String name : variableNames) {
            outputVariables.put(name, getVariable(name));
        }
        return outputVariables;
    }
}
