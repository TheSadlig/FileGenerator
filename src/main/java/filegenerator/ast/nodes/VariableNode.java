package filegenerator.ast.nodes;

import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.model.AbstractTypedVariable;

/**
 *
 * @author TheSadlig
 */
public class VariableNode extends ValueNode {

    private String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        if (variableName.charAt(0) == '$') {
            this.variableName = variableName.substring(1);
        } else {
            this.variableName = variableName;
        }
    }

    public String getValue() throws FileGeneratorException {
        if (value != null) {
            return value;
        } else {
            throw new FileGeneratorException("The variable `" + variableName + "` does not exist");
        }
    }

    @Override
    public void execute() throws FileGeneratorException {
        Environnement env = Environnement.getEnvironenement();
        AbstractTypedVariable<?> abstractVariable = env.getVariable(variableName);
        if (abstractVariable != null) {
            value = abstractVariable.toString();
        } else {
            value = null;
        }
    }

}
