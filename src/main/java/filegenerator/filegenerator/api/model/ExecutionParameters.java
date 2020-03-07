package filegenerator.filegenerator.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TheSadlig
 */
public class ExecutionParameters {

    // This map links the filename to their content
    private Map<String, String> templatesContent = new HashMap<>();

    private Map<String, String> inputVariables = new HashMap<>();

    private List<String> expectedVariables = new ArrayList<>();

    private String path;

    public Map<String, String> getTemplatesContent() {
        return templatesContent;
    }

    public String getTemplateContent(String templateName) {
        return templatesContent.get(templateName);
    }

    public void setTemplatesContent(Map<String, String> templatesContent) {
        this.templatesContent = templatesContent;
    }

    public void addTemplatesContent(String templateName, String templateContent) {
        templatesContent.put(templateName, templateContent);
    }

    public void addInputVariables(String variableName, String variableValue) {
        templatesContent.put(variableName, variableValue);
    }

    public Map<String, String> getInputVariables() {
        return inputVariables;
    }

    public void setInputVariables(Map<String, String> inputVariables) {
        this.inputVariables = inputVariables;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getExpectedVariables() {
        return expectedVariables;
    }

    public void setExpectedVariables(List<String> expectedVariables) {
        this.expectedVariables = expectedVariables;
    }

    public void addExpectedVariable(String variableName) {
        expectedVariables.add(variableName);
    }
}
