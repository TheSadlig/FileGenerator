package filegenerator.ast.nodes;

import filegenerator.execution.Environnement;
import filegenerator.execution.FileGeneratorException;
import filegenerator.filegenerator.model.AbstractTypedVariable;
import filegenerator.filegenerator.model.ArrayTypedVariable;

/**
 *
 * @author TheSadlig
 */
public class ArrayNode extends ValueNode {

    private String arrayName;
    private ValueNode indexNode;

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        if (arrayName.charAt(0) == '$') {
            this.arrayName = arrayName.substring(1);
        } else {
            this.arrayName = arrayName;
        }
    }

    public String getIndexNode() throws FileGeneratorException {
        return indexNode.getValue();
    }

    public void setIndexNode(ValueNode indexNode) {
        this.indexNode = indexNode;
    }

    @Override
    public void execute() throws FileGeneratorException {
        indexNode.execute();

        Environnement env = Environnement.getEnvironenement();
        AbstractTypedVariable<?> abstractVariable = env.getVariable(arrayName);
        if (abstractVariable != null && abstractVariable instanceof ArrayTypedVariable<?>) {
            ArrayTypedVariable<?> variable = (ArrayTypedVariable<?>) abstractVariable;

            String index = indexNode.getValue();
            value = variable.getFromArray(index).toString();
        } else {
            throw new FileGeneratorException("The array `" + arrayName + "` does not exist");
        }
    }

}
